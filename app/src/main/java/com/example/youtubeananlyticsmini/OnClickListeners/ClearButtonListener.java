package com.example.youtubeananlyticsmini.OnClickListeners;

import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class ClearButtonListener implements View.OnClickListener{

    private final TextInputEditText videoURLEditText;

    public ClearButtonListener(TextInputEditText videoURLEditText) {
        this.videoURLEditText = videoURLEditText;
    }

    @Override
    public void onClick(View v) {
        videoURLEditText.setText("");
    }
}
