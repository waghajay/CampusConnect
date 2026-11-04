package com.example.campuseventapp.models;

public class User {
    private int id;
    private String username;
    private String displayName;
    private String email;
    private String role;
    private String department;
    private String course;
    private String academicYear;

    public User(int id, String username, String displayName, String email, String role, String department, String course, String academicYear) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.role = role;
        this.department = department;
        this.course = course;
        this.academicYear = academicYear;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getDisplayName() { return displayName; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getDepartment() { return department; }
    public String getCourse() { return course; }
    public String getAcademicYear() { return academicYear; }
}