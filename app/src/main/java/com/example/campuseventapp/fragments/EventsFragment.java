package com.example.campuseventapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {

    private RecyclerView rvEvents;
    private EventAdapter adapter;
    private DatabaseHelper db;
    private SearchView searchView;
    private ChipGroup chipGroupCategories;

    private String currentSearch = "";
    private String currentCategory = "All";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        db = new DatabaseHelper(getContext());
        rvEvents = view.findViewById(R.id.rvEvents);
        searchView = view.findViewById(R.id.searchView);
        chipGroupCategories = view.findViewById(R.id.chipGroupCategories);

        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new EventAdapter(new ArrayList<>(), event -> {
            Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
            intent.putExtra("EVENT_ID", event.getId());
            startActivity(intent);
        });
        rvEvents.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentSearch = query;
                loadEvents();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearch = newText;
                loadEvents();
                return true;
            }
        });

        chipGroupCategories.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = view.findViewById(checkedId);
            if (chip != null) {
                currentCategory = chip.getText().toString();
            } else {
                currentCategory = "All";
            }
            loadEvents();
        });

        loadEvents();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadEvents();
    }

    private void loadEvents() {
        if (db != null && adapter != null) {
            List<Event> events = db.getApprovedEvents(currentSearch, currentCategory);
            adapter.setEventList(events);
        }
    }
}