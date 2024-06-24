package com.example.youtubeananlyticsmini;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeananlyticsmini.adapters.CommentAdapter;
import com.example.youtubeananlyticsmini.models.VideoFeedback;
import com.google.gson.Gson;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView commentRecyclerView;
    private VideoFeedback videoFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        commentRecyclerView = findViewById(R.id.comment_recyclerview);
        String feedbackJson = getIntent().getStringExtra(Constants.COMMENT_INTENT_KEY);
        videoFeedback = new Gson().fromJson(feedbackJson, VideoFeedback.class);

        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        commentRecyclerView.setAdapter(new CommentAdapter(videoFeedback.getComments(), getApplicationContext()));
    }
}