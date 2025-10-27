package ru.mirea.zakirovakr.scrollviewapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {

    private LinearLayout wrapper;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wrapper = findViewById(R.id.wrapper);
        scrollView = findViewById(R.id.scrollView);

        BigInteger value = BigInteger.ONE;
        for (int i = 1; i <= 100; i++) {
            value = value.shiftLeft(1);

            View view = getLayoutInflater().inflate(R.layout.item, null, false);

            TextView text = view.findViewById(R.id.textView);
            text.setText(String.format("Элемент %d: %s", i, value.toString()));

            ImageView img = view.findViewById(R.id.imageView);
            img.setImageResource(android.R.drawable.ic_dialog_info);

            wrapper.addView(view);
        }
    }
}