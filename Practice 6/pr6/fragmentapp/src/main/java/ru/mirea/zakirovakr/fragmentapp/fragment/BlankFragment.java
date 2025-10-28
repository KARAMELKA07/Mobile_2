package ru.mirea.zakirovakr.fragmentapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.mirea.zakirovakr.fragmentapp.R;

public class BlankFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(BlankFragment.class.getSimpleName(), "onCreateView");
        View view	=	inflater.inflate(R.layout.fragment_blank,	container,	false);
        return	view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        int numberStudent = requireArguments().getInt("my_number_student", 0);
        Log.d(BlankFragment.class.getSimpleName(), String.valueOf(numberStudent));
        TextView tv = view.findViewById(R.id.textNumber);
        tv.setText("Номер по списку: " + numberStudent);
    }
}

