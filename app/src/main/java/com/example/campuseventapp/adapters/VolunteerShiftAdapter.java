package com.example.campuseventapp.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuseventapp.R;
import com.example.campuseventapp.db.DatabaseHelper;
import com.example.campuseventapp.models.VolunteerShift;

import java.util.List;

public class VolunteerShiftAdapter extends RecyclerView.Adapter<VolunteerShiftAdapter.ShiftViewHolder> {

    public interface OnShiftActionListener {
        void onBookShift(VolunteerShift shift);
        void onCancelShift(VolunteerShift shift);
    }

    private List<VolunteerShift> shiftList;
    private DatabaseHelper db;
    private int userId;
    private OnShiftActionListener listener;

    public VolunteerShiftAdapter(List<VolunteerShift> shiftList, DatabaseHelper db, int userId, OnShiftActionListener listener) {
        this.shiftList = shiftList;
        this.db = db;
        this.userId = userId;
        this.listener = listener;
    }

    public void setShiftList(List<VolunteerShift> shiftList) {
        this.shiftList = shiftList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_volunteer_shift, parent, false);
        return new ShiftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftViewHolder holder, int position) {
        VolunteerShift shift = shiftList.get(position);
        holder.tvRole.setText(shift.getRoleTitle());
        holder.tvDesc.setText(shift.getDescription());
        holder.tvTime.setText(shift.getShiftDate() + " (" + shift.getStartTime() + " - " + shift.getEndTime() + ")");

        int currentBookings = db.getBookingCountForShift(shift.getId());
        int availableSlots = shift.getCapacity() - currentBookings;
        holder.tvCapacity.setText("Slots: " + Math.max(0, availableSlots) + "/" + shift.getCapacity());

        boolean isBooked = db.isShiftBookedByUser(shift.getId(), userId);

        if (isBooked) {
            holder.btnBook.setText("Cancel Shift Booking");
            holder.btnBook.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DC4545")));
            holder.btnBook.setOnClickListener(v -> listener.onCancelShift(shift));
        } else {
            if (availableSlots <= 0) {
                holder.btnBook.setText("Shift Full");
                holder.btnBook.setEnabled(false);
                holder.btnBook.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A0AEC0")));
            } else {
                holder.btnBook.setText("Book Shift");
                holder.btnBook.setEnabled(true);
                holder.btnBook.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5146E5")));
                holder.btnBook.setOnClickListener(v -> listener.onBookShift(shift));
            }
        }
    }

    @Override
    public int getItemCount() {
        return shiftList != null ? shiftList.size() : 0;
    }

    static class ShiftViewHolder extends RecyclerView.ViewHolder {
        TextView tvRole, tvDesc, tvTime, tvCapacity;
        Button btnBook;

        public ShiftViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRole = itemView.findViewById(R.id.tvRoleTitle);
            tvDesc = itemView.findViewById(R.id.tvShiftDesc);
            tvTime = itemView.findViewById(R.id.tvShiftTime);
            tvCapacity = itemView.findViewById(R.id.tvShiftCapacity);
            btnBook = itemView.findViewById(R.id.btnBookShift);
        }
    }
}