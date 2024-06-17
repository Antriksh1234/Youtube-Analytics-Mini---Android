package com.example.youtubeananlyticsmini.OnClickListeners;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class PlayVideoButtonListener implements View.OnClickListener {

    private final Context context;
    private final String videoURL;

    public PlayVideoButtonListener(Context context, String videoURL) {
        this.context = context;
        this.videoURL = videoURL;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(videoURL));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
