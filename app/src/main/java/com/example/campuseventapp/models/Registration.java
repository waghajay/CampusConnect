package com.example.campuseventapp.models;

public class Registration {
    private int id;
    private int eventId;
    private int userId;
    private String registrationDate;
    private String status;
    private String attendanceStatus;

    public Registration(int id, int eventId, int userId, String registrationDate, String status, String attendanceStatus) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.registrationDate = registrationDate;
        this.status = status;
        this.attendanceStatus = attendanceStatus;
    }

    public int getId() { return id; }
    public int getEventId() { return eventId; }
    public int getUserId() { return userId; }
    public String getRegistrationDate() { return registrationDate; }
    public String getStatus() { return status; }
    public String getAttendanceStatus() { return attendanceStatus; }
}