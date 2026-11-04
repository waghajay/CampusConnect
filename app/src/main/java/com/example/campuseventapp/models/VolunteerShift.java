package com.example.campuseventapp.models;

public class VolunteerShift {
    private int id;
    private int eventId;
    private String roleTitle;
    private String description;
    private String shiftDate;
    private String startTime;
    private String endTime;
    private int capacity;
    private String status;

    public VolunteerShift(int id, int eventId, String roleTitle, String description, String shiftDate, String startTime, String endTime, int capacity, String status) {
        this.id = id;
        this.eventId = eventId;
        this.roleTitle = roleTitle;
        this.description = description;
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.status = status;
    }

    public int getId() { return id; }
    public int getEventId() { return eventId; }
    public String getRoleTitle() { return roleTitle; }
    public String getDescription() { return description; }
    public String getShiftDate() { return shiftDate; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public int getCapacity() { return capacity; }
    public String getStatus() { return status; }
}