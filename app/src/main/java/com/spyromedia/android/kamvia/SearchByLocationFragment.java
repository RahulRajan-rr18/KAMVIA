package com.spyromedia.android.kamvia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchByLocationFragment extends Fragment {

    AutoCompleteTextView searchLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_by_location, container, false);
        searchLocation = view.findViewById(R.id.searchLocation);

        String[] itemlistvalues = {"Raju", "Balu", "Raman", "KrishnanKutty", "Gopalan", "Ashok",
                "Anjas", "Muhammed", "Chandran", "Anoop", "Aravind"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.select_dialog_item, itemlistvalues);
        //Used to specify minimum number of
        //characters the user has to type in order to display the drop down hint.
        searchLocation.setThreshold(1);
        //Setting adapter
        searchLocation.setAdapter(arrayAdapter);
        searchLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = searchLocation.getText().toString();
                Toast.makeText(getContext(), "Something Selected"+item, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
