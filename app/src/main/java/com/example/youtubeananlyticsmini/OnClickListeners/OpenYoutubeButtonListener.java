package com.example.youtubeananlyticsmini.OnClickListeners;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.example.youtubeananlyticsmini.api.NetworkChecker;

public class OpenYoutubeButtonListener implements View.OnClickListener {

    private Context context;

    public OpenYoutubeButtonListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if (!NetworkChecker.isNetworkAvailable((Application) context)) {
            Toast.makeText(context, "Please Check your internet connection and try again!!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.google.android.youtube");
        intent.setData(Uri.parse("https://www.youtube.com/"));
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // If YouTube app is not installed, open the YouTube website
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/"));
            context.startActivity(webIntent);
        }
    }
}
