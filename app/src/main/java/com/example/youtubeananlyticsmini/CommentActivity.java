package com.example.youtubeananlyticsmini;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView commentRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        commentRecyclerView = findViewById(R.id.comment_recyclerview);
    }


}