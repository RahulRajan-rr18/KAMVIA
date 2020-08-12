package com.spyromedia.android.kamvia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;


public class SearchFragment extends Fragment {

    MaterialCardView searchbynamecard, searchbyplacecard;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchbynamecard = getView().findViewById(R.id.searchbyname);
        searchbyplacecard = getView().findViewById(R.id.searchbyplace);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        searchbynamecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}