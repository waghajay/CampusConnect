package com.example.campuseventapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campuseventapp.db.DatabaseHelper;
import com.example.campuseventapp.models.Event;
import com.example.campuseventapp.utils.SessionManager;

public class EventDetailsActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private SessionManager sessionManager;
    private Event event;
    private int eventId;

    private TextView tvTitle, tvCategory, tvOrganizer, tvDate, tvVenue, tvCapacity, tvDesc;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        db = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        eventId = getIntent().getIntExtra("EVENT_ID", -1);
        if (eventId == -1) {
            Toast.makeText(this, "Invalid event", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvTitle = findViewById(R.id.tvDetailTitle);
        tvCategory = findViewById(R.id.tvDetailCategory);
        tvOrganizer = findViewById(R.id.tvDetailOrganizer);
        tvDate = findViewById(R.id.tvDetailDate);
        tvVenue = findViewById(R.id.tvDetailVenue);
        tvCapacity = findViewById(R.id.tvDetailCapacity);
        tvDesc = findViewById(R.id.tvDetailDesc);
        btnRegister = findViewById(R.id.btnRegister);

        loadEventDetails();

        btnRegister.setOnClickListener(v -> {
            int userId = sessionManager.getUserId();
            if (userId == -1) {
                Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isRegistered = db.isUserRegistered(eventId, userId);
            if (isRegistered) {
                // Cancel Registration
                boolean success = db.cancelRegistration(eventId, userId);
                if (success) {
                    Toast.makeText(this, "Registration cancelled successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to cancel registration", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Register
                boolean success = db.registerForEvent(eventId, userId);
                if (success) {
                    Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Registration failed or event full!", Toast.LENGTH_SHORT).show();
                }
            }

            loadEventDetails();
        });
    }

    private void loadEventDetails() {
        event = db.getEventById(eventId);
        if (event == null) {
            Toast.makeText(this, "Event not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvTitle.setText(event.getTitle());
        tvCategory.setText(event.getCategory());
        tvOrganizer.setText("Organized by " + event.getOrganizerName());
        tvDate.setText("Date: " + event.getEventDate() + " (" + event.getStartTime() + " - " + event.getEndTime() + ")");
        tvVenue.setText("Venue: " + event.getVenue());
        
        int availableSlots = event.getCapacity() - event.getRegisteredCount();
        tvCapacity.setText("Available Slots: " + Math.max(0, availableSlots) + " / " + event.getCapacity());
        tvDesc.setText(event.getDescription());

        int userId = sessionManager.getUserId();
        boolean isRegistered = db.isUserRegistered(eventId, userId);
        
        if (isRegistered) {
            btnRegister.setText("Cancel Registration");
            btnRegister.setBackgroundTintList(getColorStateList(R.color.status_error));
        } else {
            if (availableSlots <= 0) {
                btnRegister.setText("Event Full");
                btnRegister.setEnabled(false);
                btnRegister.setBackgroundTintList(getColorStateList(R.color.text_muted));
            } else {
                btnRegister.setText("Register For Event");
                btnRegister.setEnabled(true);
                btnRegister.setBackgroundTintList(getColorStateList(R.color.primary));
            }
        }
    }
}