package com.example.campuseventapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.campuseventapp.models.Event;
import com.example.campuseventapp.models.Registration;
import com.example.campuseventapp.models.User;
import com.example.campuseventapp.models.VolunteerBooking;
import com.example.campuseventapp.models.VolunteerShift;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CampusConnect.db";
    private static final int DATABASE_VERSION = 1;

    // Users
    private static final String TABLE_USERS = "users";
    private static final String U_ID = "id";
    private static final String U_USERNAME = "username";
    private static final String U_PASS = "password";
    private static final String U_DISPLAY = "display_name";
    private static final String U_EMAIL = "email";
    private static final String U_ROLE = "role";

    // Events
    private static final String TABLE_EVENTS = "events";
    private static final String E_ID = "id";
    private static final String E_TITLE = "title";
    private static final String E_DESC = "description";
    private static final String E_CATEGORY = "category";
    private static final String E_VENUE = "venue";
    private static final String E_DATE = "event_date";
    private static final String E_START = "start_time";
    private static final String E_END = "end_time";
    private static final String E_CAP = "capacity";
    private static final String E_ORG = "organizer_name";
    private static final String E_STATUS = "status";
    private static final String E_CREATED_BY = "created_by";

    // Registrations
    private static final String TABLE_REGS = "registrations";
    private static final String R_ID = "id";
    private static final String R_EVENT_ID = "event_id";
    private static final String R_USER_ID = "user_id";
    private static final String R_STATUS = "status";
    
    // Shifts
    private static final String TABLE_SHIFTS = "volunteer_shifts";
    private static final String S_ID = "id";
    private static final String S_EVENT_ID = "event_id";
    private static final String S_ROLE = "role_title";
    private static final String S_DESC = "description";
    private static final String S_DATE = "shift_date";
    private static final String S_START = "start_time";
    private static final String S_END = "end_time";
    private static final String S_CAP = "capacity";
    private static final String S_STATUS = "status";
    
    // Bookings
    private static final String TABLE_BOOKINGS = "volunteer_bookings";
    private static final String B_ID = "id";
    private static final String B_SHIFT_ID = "shift_id";
    private static final String B_USER_ID = "user_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsers = "CREATE TABLE " + TABLE_USERS + " (" +
                U_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                U_USERNAME + " TEXT, " +
                U_PASS + " TEXT, " +
                U_DISPLAY + " TEXT, " +
                U_EMAIL + " TEXT, " +
                U_ROLE + " TEXT, " +
                "department TEXT, course TEXT, academic_year TEXT)";
        
        String createEvents = "CREATE TABLE " + TABLE_EVENTS + " (" +
                E_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                E_TITLE + " TEXT, " +
                E_DESC + " TEXT, " +
                E_CATEGORY + " TEXT, " +
                E_VENUE + " TEXT, " +
                E_DATE + " TEXT, " +
                E_START + " TEXT, " +
                E_END + " TEXT, " +
                E_CAP + " INTEGER, " +
                E_ORG + " TEXT, " +
                E_STATUS + " TEXT, " +
                E_CREATED_BY + " INTEGER, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP)";
                
        String createRegs = "CREATE TABLE " + TABLE_REGS + " (" +
                R_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                R_EVENT_ID + " INTEGER, " +
                R_USER_ID + " INTEGER, " +
                "registration_date DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                R_STATUS + " TEXT, " +
                "attendance_status TEXT)";
                
        String createShifts = "CREATE TABLE " + TABLE_SHIFTS + " (" +
                S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                S_EVENT_ID + " INTEGER, " +
                S_ROLE + " TEXT, " +
                S_DESC + " TEXT, " + S_DATE + " TEXT, " + S_START + " TEXT, " + S_END + " TEXT, " +
                S_CAP + " INTEGER, " + S_STATUS + " TEXT)";
                
        String createBookings = "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                B_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                B_SHIFT_ID + " INTEGER, " +
                B_USER_ID + " INTEGER, " +
                "booking_date DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "status TEXT, attendance_status TEXT)";

        db.execSQL(createUsers);
        db.execSQL(createEvents);
        db.execSQL(createRegs);
        db.execSQL(createShifts);
        db.execSQL(createBookings);

        seedData(db);
    }

    private void seedData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO users (username, password, display_name, email, role, department, course, academic_year) VALUES " +
                "('student', 'student123', 'Alex Student', 'student@college.edu', 'STUDENT', 'Computer Science', 'B.Tech', 'Year 3')");
        db.execSQL("INSERT INTO users (username, password, display_name, email, role, department, course, academic_year) VALUES " +
                "('admin', 'admin123', 'Professor Admin', 'admin@college.edu', 'ADMIN', 'Administration', 'Faculty', 'N/A')");
        
        db.execSQL("INSERT INTO events (title, description, category, venue, event_date, start_time, end_time, capacity, organizer_name, status, created_by) VALUES " +
                "('Android Development Workshop', 'Hands-on training session on modern Android app development, Java, and SQLite.', 'Technical', 'Lab 101', '2027-05-10', '10:00 AM', '01:00 PM', 30, 'Google Developer Group', 'Approved', 2)");
        db.execSQL("INSERT INTO events (title, description, category, venue, event_date, start_time, end_time, capacity, organizer_name, status, created_by) VALUES " +
                "('Annual College Cultural Fest', 'A night of music, dance, theater, and live performances by top campus talent.', 'Cultural', 'Main Auditorium', '2027-06-15', '05:00 PM', '10:00 PM', 500, 'Cultural Club', 'Approved', 2)");
        db.execSQL("INSERT INTO events (title, description, category, venue, event_date, start_time, end_time, capacity, organizer_name, status, created_by) VALUES " +
                "('Inter-College Hackathon', '24-hour hackathon to solve real-world problems. Great prizes for winners!', 'Technical', 'Seminar Hall B', '2027-07-01', '09:00 AM', '09:00 AM', 100, 'CodeHub', 'Approved', 2)");
        db.execSQL("INSERT INTO events (title, description, category, venue, event_date, start_time, end_time, capacity, organizer_name, status, created_by) VALUES " +
                "('Campus Cricket Tournament', 'Annual sports competition for all departments.', 'Sports', 'Sports Ground', '2027-08-12', '08:00 AM', '04:00 PM', 80, 'Sports Board', 'Approved', 2)");

        db.execSQL("INSERT INTO volunteer_shifts (event_id, role_title, description, shift_date, start_time, end_time, capacity, status) VALUES " +
                "(1, 'Registration Desk Volunteer', 'Welcome attendees and check their registrations at the entrance desk.', '2027-05-10', '09:30 AM', '11:00 AM', 4, 'Open')");
        db.execSQL("INSERT INTO volunteer_shifts (event_id, role_title, description, shift_date, start_time, end_time, capacity, status) VALUES " +
                "(2, 'Crowd Management Volunteer', 'Assist in guiding audience to seats and managing backstage access.', '2027-06-15', '04:30 PM', '08:00 PM', 10, 'Open')");
        db.execSQL("INSERT INTO volunteer_shifts (event_id, role_title, description, shift_date, start_time, end_time, capacity, status) VALUES " +
                "(3, 'Tech Support & Mentoring', 'Help teams set up Wi-Fi and troubleshoot basic hardware/software issues.', '2027-07-01', '09:00 AM', '02:00 PM', 5, 'Open')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHIFTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        onCreate(db);
    }

    public User authenticate(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + U_USERNAME + "=? AND " + U_PASS + "=?", new String[]{username, password});
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8)
            );
            cursor.close();
            return user;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + U_ID + "=?", new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8)
            );
            cursor.close();
            return user;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    public boolean updateUserProfile(int userId, String displayName, String email, String department, String course, String academicYear) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(U_DISPLAY, displayName);
        cv.put(U_EMAIL, email);
        cv.put("department", department);
        cv.put("course", course);
        cv.put("academic_year", academicYear);
        return db.update(TABLE_USERS, cv, U_ID + "=?", new String[]{String.valueOf(userId)}) > 0;
    }

    // --- EVENTS ---

    public List<Event> getApprovedEvents(String searchQuery, String category) {
        List<Event> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_EVENTS + " WHERE " + E_STATUS + "='Approved'");
        List<String> args = new ArrayList<>();

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            query.append(" AND (" + E_TITLE + " LIKE ? OR " + E_VENUE + " LIKE ?)");
            args.add("%" + searchQuery.trim() + "%");
            args.add("%" + searchQuery.trim() + "%");
        }

        if (category != null && !category.equals("All")) {
            query.append(" AND " + E_CATEGORY + "=?");
            args.add(category);
        }

        query.append(" ORDER BY " + E_DATE + " ASC");

        Cursor cursor = db.rawQuery(query.toString(), args.toArray(new String[0]));
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Event event = extractEventFromCursor(cursor);
                event.setRegisteredCount(getRegistrationCountForEvent(event.getId()));
                list.add(event);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public List<Event> getAllEventsForAdmin() {
        List<Event> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " ORDER BY " + E_ID + " DESC", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Event event = extractEventFromCursor(cursor);
                event.setRegisteredCount(getRegistrationCountForEvent(event.getId()));
                list.add(event);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public Event getEventById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE " + E_ID + "=?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            Event event = extractEventFromCursor(cursor);
            event.setRegisteredCount(getRegistrationCountForEvent(event.getId()));
            cursor.close();
            return event;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    public boolean createEvent(String title, String desc, String category, String venue, String date, String startTime, String endTime, int capacity, String organizer, int createdBy) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(E_TITLE, title);
        cv.put(E_DESC, desc);
        cv.put(E_CATEGORY, category);
        cv.put(E_VENUE, venue);
        cv.put(E_DATE, date);
        cv.put(E_START, startTime);
        cv.put(E_END, endTime);
        cv.put(E_CAP, capacity);
        cv.put(E_ORG, organizer);
        cv.put(E_STATUS, "Pending"); // Defaults to pending
        cv.put(E_CREATED_BY, createdBy);

        return db.insert(TABLE_EVENTS, null, cv) != -1;
    }

    public boolean updateEventStatus(int eventId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(E_STATUS, status);
        return db.update(TABLE_EVENTS, cv, E_ID + "=?", new String[]{String.valueOf(eventId)}) > 0;
    }

    private Event extractEventFromCursor(Cursor cursor) {
        return new Event(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getInt(8),
                cursor.getString(9),
                cursor.getString(10),
                cursor.getInt(11),
                cursor.getString(12)
        );
    }

    // --- REGISTRATIONS ---

    public boolean registerForEvent(int eventId, int userId) {
        if (isUserRegistered(eventId, userId)) return false;

        Event event = getEventById(eventId);
        if (event == null || !"Approved".equalsIgnoreCase(event.getStatus())) return false;
        
        int currentRegs = getRegistrationCountForEvent(eventId);
        if (currentRegs >= event.getCapacity()) return false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(R_EVENT_ID, eventId);
        cv.put(R_USER_ID, userId);
        cv.put(R_STATUS, "Confirmed");
        cv.put("attendance_status", "Pending");

        long res = db.insert(TABLE_REGS, null, cv);
        return res != -1;
    }

    public boolean cancelRegistration(int eventId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_REGS, R_EVENT_ID + "=? AND " + R_USER_ID + "=?", new String[]{String.valueOf(eventId), String.valueOf(userId)}) > 0;
    }

    public boolean isUserRegistered(int eventId, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_REGS + " WHERE " + R_EVENT_ID + "=? AND " + R_USER_ID + "=?", new String[]{String.valueOf(eventId), String.valueOf(userId)});
        boolean registered = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        return registered;
    }

    public int getRegistrationCountForEvent(int eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_REGS + " WHERE " + R_EVENT_ID + "=?", new String[]{String.valueOf(eventId)});
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public List<Event> getRegisteredEventsForUser(int userId) {
        List<Event> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT e.* FROM " + TABLE_EVENTS + " e INNER JOIN " + TABLE_REGS + " r ON e." + E_ID + " = r." + R_EVENT_ID + " WHERE r." + R_USER_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Event event = extractEventFromCursor(cursor);
                event.setRegisteredCount(getRegistrationCountForEvent(event.getId()));
                list.add(event);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public int getRegistrationCountForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_REGS + " WHERE " + R_USER_ID + "=?", new String[]{String.valueOf(userId)});
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    // --- VOLUNTEER SHIFTS ---

    public List<VolunteerShift> getAvailableShifts() {
        List<VolunteerShift> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SHIFTS + " WHERE " + S_STATUS + "='Open'", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                VolunteerShift shift = new VolunteerShift(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7),
                        cursor.getString(8)
                );
                list.add(shift);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public boolean bookVolunteerShift(int shiftId, int userId) {
        if (isShiftBookedByUser(shiftId, userId)) return false;

        int bookedCount = getBookingCountForShift(shiftId);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + S_CAP + " FROM " + TABLE_SHIFTS + " WHERE " + S_ID + "=?", new String[]{String.valueOf(shiftId)});
        if (cursor != null && cursor.moveToFirst()) {
            int cap = cursor.getInt(0);
            cursor.close();
            if (bookedCount >= cap) return false;
        } else {
            if (cursor != null) cursor.close();
            return false;
        }

        SQLiteDatabase wdb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(B_SHIFT_ID, shiftId);
        cv.put(B_USER_ID, userId);
        cv.put("status", "Confirmed");
        cv.put("attendance_status", "Pending");

        return wdb.insert(TABLE_BOOKINGS, null, cv) != -1;
    }

    public boolean cancelVolunteerBooking(int shiftId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_BOOKINGS, B_SHIFT_ID + "=? AND " + B_USER_ID + "=?", new String[]{String.valueOf(shiftId), String.valueOf(userId)}) > 0;
    }

    public boolean isShiftBookedByUser(int shiftId, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOKINGS + " WHERE " + B_SHIFT_ID + "=? AND " + B_USER_ID + "=?", new String[]{String.valueOf(shiftId), String.valueOf(userId)});
        boolean isBooked = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        return isBooked;
    }

    public int getBookingCountForShift(int shiftId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_BOOKINGS + " WHERE " + B_SHIFT_ID + "=?", new String[]{String.valueOf(shiftId)});
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getVolunteerShiftCountForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_BOOKINGS + " WHERE " + B_USER_ID + "=?", new String[]{String.valueOf(userId)});
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public boolean createVolunteerShift(int eventId, String roleTitle, String desc, String shiftDate, String startTime, String endTime, int capacity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(S_EVENT_ID, eventId);
        cv.put(S_ROLE, roleTitle);
        cv.put(S_DESC, desc);
        cv.put(S_DATE, shiftDate);
        cv.put(S_START, startTime);
        cv.put(S_END, endTime);
        cv.put(S_CAP, capacity);
        cv.put(S_STATUS, "Open");
        return db.insert(TABLE_SHIFTS, null, cv) != -1;
    }

    // --- DASHBOARD METRICS ---

    public int getAvailableEventsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_EVENTS + " WHERE " + E_STATUS + "='Approved'", null);
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getTotalEventsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_EVENTS, null);
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getPendingEventsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_EVENTS + " WHERE " + E_STATUS + "='Pending'", null);
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getTotalRegistrationsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_REGS, null);
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getTotalVolunteerBookingsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_BOOKINGS, null);
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }
}