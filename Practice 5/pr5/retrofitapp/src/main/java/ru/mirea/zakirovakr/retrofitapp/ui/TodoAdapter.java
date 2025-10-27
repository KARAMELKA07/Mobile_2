package ru.mirea.zakirovakr.retrofitapp.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mirea.zakirovakr.retrofitapp.R;
import ru.mirea.zakirovakr.retrofitapp.model.Todo;
import ru.mirea.zakirovakr.retrofitapp.network.ApiService;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    private static final String TAG = "TodoAdapter";

    private final LayoutInflater inflater;
    private final List<Todo> todos;
    private final ApiService apiService;

    private static final String[] IMAGE_URLS = {
            "https://avatars.mds.yandex.net/get-shedevrum/12961523/8f1a4adcbb8411eea967fe19746b188b/orig",
            "https://avatars.mds.yandex.net/get-shedevrum/14347134/img_057b5bcbb29311f0b5ac86015376f2c1/orig",
            "https://avatars.mds.yandex.net/get-shedevrum/15403316/img_01737f5fb0c711f0bce0663089598102/orig",
            "https://avatars.mds.yandex.net/get-shedevrum/16287116/img_0cf5535fb2b911f0baf7c236a532996a/orig"
    };

    public TodoAdapter(Context context, List<Todo> todos, ApiService apiService) {
        this.inflater = LayoutInflater.from(context);
        this.todos = todos;
        this.apiService = apiService;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todos.get(position);

        holder.checkBoxCompleted.setOnCheckedChangeListener(null);
        holder.textViewTitle.setText(todo.getTitle());
        holder.checkBoxCompleted.setChecked(Boolean.TRUE.equals(todo.getCompleted()));

        int base = (todo.getId() != null ? todo.getId() : position);
        String imageUrl = IMAGE_URLS[ Math.abs(base) % IMAGE_URLS.length ];

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .resize(64, 64)
                .centerCrop()
                .into(holder.imageThumb);

        holder.checkBoxCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                todo.setCompleted(isChecked);

                apiService.updateTodo(todo.getId(), todo).enqueue(new Callback<Todo>() {
                    @Override
                    public void onResponse(Call<Todo> call, Response<Todo> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(inflater.getContext(),
                                    "Обновлено: id=" + todo.getId() + ", completed=" + isChecked,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "Update error: " + response.code());
                            Toast.makeText(inflater.getContext(),
                                    "Ошибка обновления: " + response.code(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Todo> call, Throwable t) {
                        Log.e(TAG, "Network error: " + t.getMessage());
                        Toast.makeText(inflater.getContext(),
                                "Сеть недоступна: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }
}


