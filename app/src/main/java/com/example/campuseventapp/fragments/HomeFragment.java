package com.example.campuseventapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuseventapp.EventDetailsActivity;
import com.example.campuseventapp.R;
import com.example.campuseventapp.adapters.EventAdapter;
import com.example.campuseventapp.db.DatabaseHelper;
import com.example.campuseventapp.models.Event;
import com.example.campuseventapp.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private DatabaseHelper db;
    private SessionManager sessionManager;

    private TextView tvGreeting, tvMetricAvailable, tvMetricRegistered, tvMetricVolunteer;
    private RecyclerView rvHomeUpcoming;
    private EventAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = new DatabaseHelper(getContext());
        sessionManager = new SessionManager(getContext());

        tvGreeting = view.findViewById(R.id.tvGreeting);
        tvMetricAvailable = view.findViewById(R.id.tvMetricAvailable);
        tvMetricRegistered = view.findViewById(R.id.tvMetricRegistered);
        tvMetricVolunteer = view.findViewById(R.id.tvMetricVolunteer);
        rvHomeUpcoming = view.findViewById(R.id.rvHomeUpcoming);

        rvHomeUpcoming.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new EventAdapter(new ArrayList<>(), event -> {
            Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
            intent.putExtra("EVENT_ID", event.getId());
            startActivity(intent);
        });
        rvHomeUpcoming.setAdapter(adapter);

        updateDashboard();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDashboard();
    }

    private void updateDashboard() {
        if (sessionManager == null || db == null) return;

        String displayName = sessionManager.getDisplayName();
        tvGreeting.setText("Welcome back, " + (displayName.isEmpty() ? "Student" : displayName) + "!");

        int userId = sessionManager.getUserId();
        int availableCount = db.getAvailableEventsCount();
        int registeredCount = db.getRegistrationCountForUser(userId);
        int volunteerCount = db.getVolunteerShiftCountForUser(userId);

        tvMetricAvailable.setText(String.valueOf(availableCount));
        tvMetricRegistered.setText(String.valueOf(registeredCount));
        tvMetricVolunteer.setText(String.valueOf(volunteerCount));

        List<Event> upcomingEvents = db.getApprovedEvents("", "All");
        adapter.setEventList(upcomingEvents);
    }
}