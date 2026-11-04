package com.example.campuseventapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuseventapp.R;
import com.example.campuseventapp.adapters.VolunteerShiftAdapter;
import com.example.campuseventapp.db.DatabaseHelper;
import com.example.campuseventapp.models.VolunteerShift;
import com.example.campuseventapp.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class VolunteerFragment extends Fragment {

    private DatabaseHelper db;
    private SessionManager sessionManager;
    private RecyclerView rvShifts;
    private View tvEmpty;
    private VolunteerShiftAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteer, container, false);

        db = new DatabaseHelper(getContext());
        sessionManager = new SessionManager(getContext());

        rvShifts = view.findViewById(R.id.rvShifts);
        tvEmpty = view.findViewById(R.id.tvEmptyShifts);

        rvShifts.setLayoutManager(new LinearLayoutManager(getContext()));
        
        int userId = sessionManager.getUserId();
        adapter = new VolunteerShiftAdapter(new ArrayList<>(), db, userId, new VolunteerShiftAdapter.OnShiftActionListener() {
            @Override
            public void onBookShift(VolunteerShift shift) {
                boolean success = db.bookVolunteerShift(shift.getId(), userId);
                if (success) {
                    Toast.makeText(getContext(), "Shift booked successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Booking failed or shift full!", Toast.LENGTH_SHORT).show();
                }
                loadShifts();
            }

            @Override
            public void onCancelShift(VolunteerShift shift) {
                boolean success = db.cancelVolunteerBooking(shift.getId(), userId);
                if (success) {
                    Toast.makeText(getContext(), "Booking cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to cancel booking", Toast.LENGTH_SHORT).show();
                }
                loadShifts();
            }
        });
        rvShifts.setAdapter(adapter);

        loadShifts();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadShifts();
    }

    private void loadShifts() {
        if (db == null) return;
        List<VolunteerShift> shifts = db.getAvailableShifts();
        if (shifts.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvShifts.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvShifts.setVisibility(View.VISIBLE);
            adapter.setShiftList(shifts);
        }
    }
}