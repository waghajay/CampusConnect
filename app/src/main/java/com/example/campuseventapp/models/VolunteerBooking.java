package com.example.campuseventapp.models;

public class VolunteerBooking {
    private int id;
    private int shiftId;
    private int userId;
    private String bookingDate;
    private String status;
    private String attendanceStatus;

    public VolunteerBooking(int id, int shiftId, int userId, String bookingDate, String status, String attendanceStatus) {
        this.id = id;
        this.shiftId = shiftId;
        this.userId = userId;
        this.bookingDate = bookingDate;
        this.status = status;
        this.attendanceStatus = attendanceStatus;
    }

    public int getId() { return id; }
    public int getShiftId() { return shiftId; }
    public int getUserId() { return userId; }
    public String getBookingDate() { return bookingDate; }
    public String getStatus() { return status; }
    public String getAttendanceStatus() { return attendanceStatus; }
}