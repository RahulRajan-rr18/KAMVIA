package com.spyromedia.android.kamvia.AdminFunctions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.spyromedia.android.kamvia.AddOrdersOrCirculars;
import com.spyromedia.android.kamvia.R;

public class PostsActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnNewPost, btnModify, btnOldPost, btnAddCircular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        getSupportActionBar().hide();


        btnNewPost = findViewById(R.id.btnNewPost);
        btnModify = findViewById(R.id.btnModify);
        btnOldPost = findViewById(R.id.btnOldPosts);
        btnAddCircular = findViewById(R.id.btnAddCircular);

        btnNewPost.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnOldPost.setOnClickListener(this);
        btnAddCircular.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnNewPost:
                Intent newpost = new Intent(PostsActivity.this, AddNewPostActivity.class);
                startActivity(newpost);
                break;

            case R.id.btnModify:
                Intent modifypost = new Intent(PostsActivity.this, ModifyPostListActivity.class);
                startActivity(modifypost);
                break;
            case R.id.btnOldPosts:
                Intent oldpost = new Intent(PostsActivity.this, ListOldPostsActivity.class);
                startActivity(oldpost);
                break;

            case R.id.btnAddCircular:
                Intent addCircular = new Intent(PostsActivity.this, AddOrdersOrCirculars.class);
                startActivity(addCircular);
                break;


            default:
                break;
        }
    }
}