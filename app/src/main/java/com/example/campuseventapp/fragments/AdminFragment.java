package com.example.campuseventapp.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuseventapp.R;
import com.example.campuseventapp.adapters.AdminEventAdapter;
import com.example.campuseventapp.db.DatabaseHelper;
import com.example.campuseventapp.models.Event;
import com.example.campuseventapp.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class AdminFragment extends Fragment {

    private DatabaseHelper db;
    private SessionManager sessionManager;

    private TextView tvTotalEvents, tvPendingEvents, tvTotalRegs, tvTotalVolunteers;
    private EditText etTitle, etCategory, etVenue, etDate, etCapacity, etDesc;
    private Button btnCreate;
    private RecyclerView rvAdminEvents;
    private AdminEventAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        db = new DatabaseHelper(getContext());
        sessionManager = new SessionManager(getContext());

        tvTotalEvents = view.findViewById(R.id.tvAdminTotalEvents);
        tvPendingEvents = view.findViewById(R.id.tvAdminPendingEvents);
        tvTotalRegs = view.findViewById(R.id.tvAdminTotalRegs);
        tvTotalVolunteers = view.findViewById(R.id.tvAdminTotalVolunteers);

        etTitle = view.findViewById(R.id.etCreateTitle);
        etCategory = view.findViewById(R.id.etCreateCategory);
        etVenue = view.findViewById(R.id.etCreateVenue);
        etDate = view.findViewById(R.id.etCreateDate);
        etCapacity = view.findViewById(R.id.etCreateCapacity);
        etDesc = view.findViewById(R.id.etCreateDesc);
        btnCreate = view.findViewById(R.id.btnCreateEvent);

        rvAdminEvents = view.findViewById(R.id.rvAdminEvents);
        rvAdminEvents.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AdminEventAdapter(new ArrayList<>(), new AdminEventAdapter.OnAdminActionListener() {
            @Override
            public void onApprove(Event event) {
                db.updateEventStatus(event.getId(), "Approved");
                Toast.makeText(getContext(), "Event Approved!", Toast.LENGTH_SHORT).show();
                loadAdminData();
            }

            @Override
            public void onReject(Event event) {
                db.updateEventStatus(event.getId(), "Rejected");
                Toast.makeText(getContext(), "Event Rejected!", Toast.LENGTH_SHORT).show();
                loadAdminData();
            }
        });
        rvAdminEvents.setAdapter(adapter);

        btnCreate.setOnClickListener(v -> createEvent());

        loadAdminData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAdminData();
    }

    private void createEvent() {
        String title = etTitle.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String venue = etVenue.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String capStr = etCapacity.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(category) || TextUtils.isEmpty(venue) || TextUtils.isEmpty(date) || TextUtils.isEmpty(capStr)) {
            Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int capacity;
        try {
            capacity = Integer.parseInt(capStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid capacity", Toast.LENGTH_SHORT).show();
            return;
        }

        int adminId = sessionManager.getUserId();
        String organizer = sessionManager.getDisplayName();

        boolean created = db.createEvent(title, desc, category, venue, date, "10:00 AM", "01:00 PM", capacity, organizer.isEmpty() ? "Admin" : organizer, adminId);
        if (created) {
            Toast.makeText(getContext(), "Event created (Pending Approval)!", Toast.LENGTH_SHORT).show();
            clearInputs();
            loadAdminData();
        } else {
            Toast.makeText(getContext(), "Failed to create event", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInputs() {
        etTitle.setText("");
        etCategory.setText("");
        etVenue.setText("");
        etDate.setText("");
        etCapacity.setText("");
        etDesc.setText("");
    }

    private void loadAdminData() {
        if (db == null) return;

        tvTotalEvents.setText(String.valueOf(db.getTotalEventsCount()));
        tvPendingEvents.setText(String.valueOf(db.getPendingEventsCount()));
        tvTotalRegs.setText(String.valueOf(db.getTotalRegistrationsCount()));
        tvTotalVolunteers.setText(String.valueOf(db.getTotalVolunteerBookingsCount()));

        List<Event> allEvents = db.getAllEventsForAdmin();
        adapter.setEventList(allEvents);
    }
}