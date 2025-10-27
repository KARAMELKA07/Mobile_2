package ru.mirea.zakirovakr.retrofitapp.ui;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.mirea.zakirovakr.retrofitapp.R;

public class TodoViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageThumb;
    public TextView textViewTitle;
    public CheckBox checkBoxCompleted;

    public TodoViewHolder(@NonNull View itemView) {
        super(itemView);
        imageThumb = itemView.findViewById(R.id.imageThumb);
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
    }
}

