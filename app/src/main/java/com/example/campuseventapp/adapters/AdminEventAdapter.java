package com.example.campuseventapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuseventapp.R;
import com.example.campuseventapp.models.Event;

import java.util.List;

public class AdminEventAdapter extends RecyclerView.Adapter<AdminEventAdapter.AdminViewHolder> {

    public interface OnAdminActionListener {
        void onApprove(Event event);
        void onReject(Event event);
    }

    private List<Event> eventList;
    private OnAdminActionListener listener;

    public AdminEventAdapter(List<Event> eventList, OnAdminActionListener listener) {
        this.eventList = eventList;
        this.listener = listener;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_event, parent, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.tvTitle.setText(event.getTitle());
        holder.tvStatus.setText(event.getStatus());
        holder.tvDetails.setText(event.getEventDate() + " | " + event.getVenue() + " | Cap: " + event.getCapacity());

        if ("Approved".equalsIgnoreCase(event.getStatus())) {
            holder.tvStatus.setTextColor(Color.parseColor("#2E7D32"));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#E8F5E9"));
            holder.layoutActions.setVisibility(View.GONE);
        } else if ("Rejected".equalsIgnoreCase(event.getStatus())) {
            holder.tvStatus.setTextColor(Color.parseColor("#C62828"));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#FFEBEE"));
            holder.layoutActions.setVisibility(View.GONE);
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#EF6C00"));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#FFF3E0"));
            holder.layoutActions.setVisibility(View.VISIBLE);

            holder.btnApprove.setOnClickListener(v -> listener.onApprove(event));
            holder.btnReject.setOnClickListener(v -> listener.onReject(event));
        }
    }

    @Override
    public int getItemCount() {
        return eventList != null ? eventList.size() : 0;
    }

    static class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvStatus, tvDetails;
        LinearLayout layoutActions;
        Button btnApprove, btnReject;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvAdminEventTitle);
            tvStatus = itemView.findViewById(R.id.tvAdminEventStatus);
            tvDetails = itemView.findViewById(R.id.tvAdminEventDetails);
            layoutActions = itemView.findViewById(R.id.layoutApprovalActions);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
    }
}