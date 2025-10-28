package ru.mirea.zakirovakr.fragmentresultapi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DataFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText editText = view.findViewById(R.id.editTextInfo);
        Button btnOpen = view.findViewById(R.id.buttonOpenBottomSheet);

        btnOpen.setOnClickListener(v -> {
            String text = editText.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putString("key", text);

            getChildFragmentManager().setFragmentResult("requestKey", bundle);

            BottomSheetFragment bottom = new BottomSheetFragment();
            bottom.show(getChildFragmentManager(), "ModalBottomSheet");
        });
    }
}

