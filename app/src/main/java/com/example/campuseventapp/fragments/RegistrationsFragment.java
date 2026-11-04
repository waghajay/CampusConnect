package com.example.campuseventapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class RegistrationsFragment extends Fragment {

    private DatabaseHelper db;
    private SessionManager sessionManager;
    private RecyclerView rvRegistrations;
    private View tvEmpty;
    private EventAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrations, container, false);

        db = new DatabaseHelper(getContext());
        sessionManager = new SessionManager(getContext());

        rvRegistrations = view.findViewById(R.id.rvRegistrations);
        tvEmpty = view.findViewById(R.id.tvEmptyRegistrations);

        rvRegistrations.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new EventAdapter(new ArrayList<>(), event -> {
            Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
            intent.putExtra("EVENT_ID", event.getId());
            startActivity(intent);
        });
        rvRegistrations.setAdapter(adapter);

        loadRegistrations();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRegistrations();
    }

    private void loadRegistrations() {
        if (sessionManager == null || db == null) return;
        int userId = sessionManager.getUserId();
        List<Event> registeredEvents = db.getRegisteredEventsForUser(userId);

        if (registeredEvents.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvRegistrations.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvRegistrations.setVisibility(View.VISIBLE);
            adapter.setEventList(registeredEvents);
        }
    }
}