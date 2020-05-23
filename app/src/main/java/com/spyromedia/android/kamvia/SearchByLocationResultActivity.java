package com.spyromedia.android.kamvia;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class SearchByLocationResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;
    private List<SearchResultRecyItem> resultList;


    TextView ClassName, SpeakerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_location_result);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setLayoutManager(new LinearLayoutManager ( this));

        resultList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            SearchResultRecyItem speakerListItem = new SearchResultRecyItem("Albert", "Kunnamkulam","KL53");
            resultList.add(speakerListItem);
        }

        adapter = new SearchResultAdapter(resultList, this);

        recyclerView.setAdapter(adapter);

    }
}
