package com.example.youtubeananlyticsmini;

import android.content.Intent;
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
        Button clearButton = findViewById(R.id.clear_button);
        Button openYoutubeButton = findViewById(R.id.open_youtube_button);

        setEditTextValue(videoURLEditText);

        submitButton.setOnClickListener(new SubmitButtonListener(videoURLEditText, handler, getApplicationContext(), MainActivity.this));
        clearButton.setOnClickListener(new ClearButtonListener(videoURLEditText));
        openYoutubeButton.setOnClickListener(new OpenYoutubeButtonListener(getApplicationContext()));
    }

    private void setEditTextValue(TextInputEditText videoURLEditText) {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        String value = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (value != null) {
            videoURLEditText.setText(value);
        }
    }
}