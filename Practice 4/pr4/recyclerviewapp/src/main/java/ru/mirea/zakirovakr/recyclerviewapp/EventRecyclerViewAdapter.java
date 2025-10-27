package ru.mirea.zakirovakr.recyclerviewapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventViewHolder> {
    private final List<Event> events;
    private Context context;

    public EventRecyclerViewAdapter(List<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View item = LayoutInflater.from(context).inflate(R.layout.event_item_view, parent, false);
        return new EventViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event e = events.get(position);

        int resId = context.getResources().getIdentifier(e.getImageName(), "drawable", context.getPackageName());
        if (resId != 0) {
            holder.getImageEvent().setImageResource(resId);
        } else {
            holder.getImageEvent().setImageResource(android.R.drawable.ic_menu_info_details);
        }

        holder.getTextEventTitle().setText(e.getTitle());
        holder.getTextEventDesc().setText(e.getDescription());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}

