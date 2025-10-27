package ru.mirea.zakirovakr.recyclerviewapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventViewHolder extends RecyclerView.ViewHolder {
    private final ImageView imageEvent;
    private final TextView textEventTitle;
    private final TextView textEventDesc;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        imageEvent     = itemView.findViewById(R.id.imageEvent);
        textEventTitle = itemView.findViewById(R.id.textEventTitle);
        textEventDesc  = itemView.findViewById(R.id.textEventDesc);
    }

    public ImageView getImageEvent() { return imageEvent; }
    public TextView getTextEventTitle() { return textEventTitle; }
    public TextView getTextEventDesc() { return textEventDesc; }
}

