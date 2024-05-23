package com.example.youtubeananlyticsmini.OnClickListeners;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.youtubeananlyticsmini.AnalyticActivity;
import com.example.youtubeananlyticsmini.Constants;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.youtubeananlyticsmini.VideoFeedback;

public class SubmitButtonListener implements View.OnClickListener {

    private final TextInputEditText videoURLEditText;
    private final Handler handler;
    private final ProgressBar progressBar;

    private Context context;

    public SubmitButtonListener(TextInputEditText videoURLEditText, Handler handler, ProgressBar progressBar, Context context) {
        this.videoURLEditText = videoURLEditText;
        this.handler = handler;
        this.progressBar = progressBar;
        this.context = context;
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

        //Start searching in the API and show user that process has started
        progressBar.setVisibility(View.VISIBLE);

        fetchVideoFeedback(url);
    }

    private void fetchVideoFeedback(String videoURL) {
        BackgroundTask task = new BackgroundTask(videoURL, handler, progressBar, context);
        task.start();
    }
}

class BackgroundTask extends Thread {
    private String videoURL = "";
    private final String APIEndpoint = "https://ymmhrwoyoj.execute-api.ap-south-1.amazonaws.com/getVideoFeedback";
    private final String TAG = "MainActivity:SubmitButtonListener";
    private final Handler handler;
    private final ProgressBar progressBar;

    private final Context context;

    BackgroundTask(String videoURL, Handler handler, ProgressBar progressBar, Context context) {
        this.videoURL = videoURL;
        this.handler = handler;
        this.progressBar = progressBar;
        this.context = context;
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
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(context, AnalyticActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constants.INTENT_KEY, response.toString());
                    context.startActivity(intent);
                });
            } else {
                Log.e(TAG, "Connection failed Response code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Got an exception while communicating with API: " + e.getLocalizedMessage());
        } finally {
            handler.post(() -> progressBar.setVisibility(View.GONE));
        }
    }
}
