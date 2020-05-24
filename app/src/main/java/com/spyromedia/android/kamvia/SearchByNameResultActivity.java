package com.spyromedia.android.kamvia;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class SearchByNameResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;
    private List<SearchResultRecyItem> resultList;


    //TextView ClassName, SpeakerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_name_result);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setLayoutManager(new LinearLayoutManager ( this));

        resultList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            SearchResultRecyItem searchResultItem = new SearchResultRecyItem("Albert", "Kunnamkulam","KL52");
            resultList.add(searchResultItem);
        }

        adapter = new SearchResultAdapter(resultList, this);

        recyclerView.setAdapter(adapter);

    }
}
