package com.spyromedia.android.kamvia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchByNameFragement extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_by_name, container, false);

        String[] itemlistvalues = {"Raju", "Balu", "Raman", "KrishnanKutty", "Gopalan", "Ashok",
                "Anjas", "Muhammed", "Chandran", "Anoop", "Aravind"};

        AutoCompleteTextView SearchByname = view.findViewById(R.id.searchbyname);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.select_dialog_item, itemlistvalues);
        SearchByname.setAdapter(arrayAdapter);

        SearchByname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getContext(), "Hola...", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }
}
