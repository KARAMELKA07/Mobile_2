package ru.mirea.zakirovakr.fragmentapp.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.zakirovakr.fragmentapp.R;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putInt("my_number_student", 10);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, BlankFragment.class, args)
                    .commit();
        }
    }
}
