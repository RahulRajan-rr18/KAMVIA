package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNewPostActivity extends AppCompatActivity {

    EditText postHead ;
    EditText postContent;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_post);
        Button chooseFile , uploadPost;
        postHead = findViewById(R.id.postheading);
        postContent = findViewById(R.id.postcontent);

        uploadPost = findViewById(R.id.buttonuploadpost);
        uploadPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean verify = verifyPost();

                if(verify == true){
                    // add file post to database
                }
            }
        });

    }
    Boolean verifyPost() {

        if (postHead.getText().toString().isEmpty()){
            postHead.setError("Add a heading");
            return false;
        }

        if (postContent.getText().toString().isEmpty()) {
            postContent.setError("Type the content");
            return false;
        }
        return true;
    }


}
