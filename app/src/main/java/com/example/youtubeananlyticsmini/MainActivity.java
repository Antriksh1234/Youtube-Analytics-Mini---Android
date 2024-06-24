package com.example.youtubeananlyticsmini;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.youtubeananlyticsmini.OnClickListeners.ClearButtonListener;
import com.example.youtubeananlyticsmini.OnClickListeners.OpenYoutubeButtonListener;
import com.example.youtubeananlyticsmini.OnClickListeners.SubmitButtonListener;
import com.google.android.material.textfield.TextInputEditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextInputEditText videoURLEditText = findViewById(R.id.video_url_edit_text);
        Button submitButton = findViewById(R.id.submitButton);
        Handler handler = new Handler();
        ProgressBar progressBar = findViewById(R.id.stats_fetch_progressbar);
        Button clearButton = findViewById(R.id.clear_button);
        Button openYoutubeButton = findViewById(R.id.open_youtube_button);

        submitButton.setOnClickListener(new SubmitButtonListener(videoURLEditText, handler, progressBar, getApplicationContext()));
        clearButton.setOnClickListener(new ClearButtonListener(videoURLEditText));
        openYoutubeButton.setOnClickListener(new OpenYoutubeButtonListener(getApplicationContext()));
    }
}