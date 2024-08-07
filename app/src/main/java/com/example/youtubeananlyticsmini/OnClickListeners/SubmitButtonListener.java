package com.example.youtubeananlyticsmini.OnClickListeners;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.youtubeananlyticsmini.AnalyticActivity;
import com.example.youtubeananlyticsmini.R;
import com.example.youtubeananlyticsmini.utils.Constants;
import com.example.youtubeananlyticsmini.api.NetworkChecker;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubmitButtonListener implements View.OnClickListener {

    private final TextInputEditText videoURLEditText;
    private final Handler handler;

    private Context context;

    private Dialog dialog;

    public SubmitButtonListener(TextInputEditText videoURLEditText, Handler handler, Context context, Context activityContext) {
        this.videoURLEditText = videoURLEditText;
        this.handler = handler;
        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_feedback_view, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activityContext, R.style.AlertDialog_Custom_Background);
        builder.setView(v);
        dialog = builder.create();
    }

    private boolean isValidURL(String url) {
        String videoId = null;

        // YouTube URL regex pattern for both regular and short-form URLs
        String youtubeRegex = "(?:youtube.com/(?:[^/]+/.+/|(?:v|e(?:mbed)?)/|.*[?&]v=)|youtu.be/)([^\"&?/]{11})";
        Pattern compiledPattern = Pattern.compile(youtubeRegex);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            videoId = matcher.group(1);
        }

        return videoId != null;
    }

    @Override
    public void onClick(View view) {
        if (videoURLEditText.getText() == null) return;

        //Lets get the URL from the EditText and try to fetch details from our backend
        String url = videoURLEditText.getText().toString();
        if (!isValidURL(url)) {
            Toast.makeText(view.getContext(), "Please Provide a correct URL!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!NetworkChecker.isNetworkAvailable((Application) context)) {
            Toast.makeText(view.getContext(), "Please Check your internet connection and try again!!", Toast.LENGTH_SHORT).show();
            return;
        }

        //Start searching in the API and show user that process has started
        dialog.show();
        fetchVideoFeedback(url);
    }

    private void fetchVideoFeedback(String videoURL) {
        BackgroundTask task = new BackgroundTask(videoURL, handler, context, videoURLEditText, dialog);
        task.start();
    }
}

class BackgroundTask extends Thread {
    private String videoURL = "";
    private final String APIEndpoint = "API-Gateway-Endpoint";
    private final String TAG = "MainActivity:SubmitButtonListener";
    private final Handler handler;

    private final Context context;

    private TextInputEditText videoURLEditText;

    private Dialog dialog;

    BackgroundTask(String videoURL, Handler handler, Context context, TextInputEditText videoURLEditText, Dialog dialog) {
        this.videoURL = videoURL;
        this.handler = handler;
        this.context = context;
        this.videoURLEditText = videoURLEditText;
        this.dialog = dialog;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(APIEndpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "text/plain");

            Log.i(TAG, "Making request for Youtube Video: " + videoURL);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(videoURL.getBytes());
            outputStream.flush();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                Log.i(TAG, "Response Body: " + response.toString());

                //Update UI
                handler.post(() -> {
                    videoURLEditText.setText("");
                    Intent intent = new Intent(context, AnalyticActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constants.INTENT_KEY, response.toString());
                    intent.putExtra(Constants.VIDEO_LINK_INTENT_KEY, videoURL);
                    context.startActivity(intent);
                });
            } else {
                Log.e(TAG, "Connection failed Response code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Got an exception while communicating with API: " + e.getLocalizedMessage());
        } finally {
            handler.post(() -> dialog.dismiss());
        }
    }
}
