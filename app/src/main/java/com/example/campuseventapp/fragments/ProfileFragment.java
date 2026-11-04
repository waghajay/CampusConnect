package com.example.campuseventapp.fragments;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.campuseventapp.LoginActivity;
import com.example.campuseventapp.R;
import com.example.campuseventapp.db.DatabaseHelper;
import com.example.campuseventapp.models.User;
import com.example.campuseventapp.utils.SessionManager;

public class ProfileFragment extends Fragment {

    private DatabaseHelper db;
    private SessionManager sessionManager;

    private TextView tvUsername, tvRole;
    private EditText etDisplayName, etEmail, etDept, etCourse, etYear;
    private Button btnSave, btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        db = new DatabaseHelper(getContext());
        sessionManager = new SessionManager(getContext());

        tvUsername = view.findViewById(R.id.tvProfileUsername);
        tvRole = view.findViewById(R.id.tvProfileRole);
        etDisplayName = view.findViewById(R.id.etProfileDisplayName);
        etEmail = view.findViewById(R.id.etProfileEmail);
        etDept = view.findViewById(R.id.etProfileDept);
        etCourse = view.findViewById(R.id.etProfileCourse);
        etYear = view.findViewById(R.id.etProfileYear);
        btnSave = view.findViewById(R.id.btnSaveProfile);
        btnLogout = view.findViewById(R.id.btnLogout);

        loadUserProfile();

        btnSave.setOnClickListener(v -> {
            int userId = sessionManager.getUserId();
            String name = etDisplayName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String dept = etDept.getText().toString().trim();
            String course = etCourse.getText().toString().trim();
            String year = etYear.getText().toString().trim();

            boolean updated = db.updateUserProfile(userId, name, email, dept, course, year);
            if (updated) {
                // Update session
                sessionManager.createLoginSession(userId, sessionManager.getUsername(), sessionManager.getRole(), name);
                Toast.makeText(getContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogout.setOnClickListener(v -> {
            sessionManager.logoutUser();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }

    private void loadUserProfile() {
        int userId = sessionManager.getUserId();
        User user = db.getUserById(userId);
        if (user != null) {
            tvUsername.setText("@" + user.getUsername());
            tvRole.setText("ROLE: " + user.getRole());
            etDisplayName.setText(user.getDisplayName());
            etEmail.setText(user.getEmail());
            etDept.setText(user.getDepartment());
            etCourse.setText(user.getCourse());
            etYear.setText(user.getAcademicYear());
        }
    }
}