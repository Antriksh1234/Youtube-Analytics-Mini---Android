package com.example.youtubeananlyticsmini;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.youtubeananlyticsmini.OnClickListeners.SubmitButtonListener;
import com.google.android.material.textfield.TextInputEditText;


public class MainActivity extends AppCompatActivity {

    private TextInputEditText videoURLEditText;
    private Button submitButton;
    private Handler handler;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoURLEditText = findViewById(R.id.video_url_edit_text);
        submitButton = findViewById(R.id.submitButton);
        handler = new Handler();
        progressBar = findViewById(R.id.stats_fetch_progressbar);

        submitButton.setOnClickListener(new SubmitButtonListener(videoURLEditText, handler, progressBar, getApplicationContext()));
    }
}