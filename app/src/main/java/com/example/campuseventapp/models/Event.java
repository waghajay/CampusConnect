package com.example.campuseventapp.models;

public class Event {
    private int id;
    private String title;
    private String description;
    private String category;
    private String venue;
    private String eventDate;
    private String startTime;
    private String endTime;
    private int capacity;
    private String organizerName;
    private String status;
    private int createdBy;
    private String createdAt;
    
    // Additional fields useful for UI
    private int registeredCount;

    public Event(int id, String title, String description, String category, String venue, String eventDate, String startTime, String endTime, int capacity, String organizerName, String status, int createdBy, String createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.venue = venue;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.organizerName = organizerName;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getVenue() { return venue; }
    public String getEventDate() { return eventDate; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public int getCapacity() { return capacity; }
    public String getOrganizerName() { return organizerName; }
    public String getStatus() { return status; }
    public int getCreatedBy() { return createdBy; }
    public String getCreatedAt() { return createdAt; }

    public int getRegisteredCount() { return registeredCount; }
    public void setRegisteredCount(int registeredCount) { this.registeredCount = registeredCount; }
}