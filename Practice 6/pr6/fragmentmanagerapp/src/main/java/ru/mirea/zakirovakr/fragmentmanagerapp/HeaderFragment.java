package ru.mirea.zakirovakr.fragmentmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
public class HeaderFragment extends Fragment {
    private ShareViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_header, container, false);
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);

        ListView listView = view.findViewById(R.id.listView);
        String[] countries = {"Brazil", "China", "India", "Russia", "South Africa"};
        listView.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, countries));

        listView.setOnItemClickListener((AdapterView<?> parent, View v, int pos, long id) ->
                viewModel.setSomeValue(countries[pos]));
    }
}
