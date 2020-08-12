package com.spyromedia.android.kamvia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.card.MaterialCardView;


public class SearchFragment extends Fragment {

    MaterialCardView searchbynamecard, searchbyplacecard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchbynamecard = view.findViewById(R.id.searchbyname);
        searchbyplacecard = view.findViewById(R.id.searchbyplace);
        searchbynamecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.searchByNameFragement);
            }
        });
        searchbyplacecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.searchByLocationFragment);
            }
        });
        return view;
    }
}