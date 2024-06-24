package com.example.youtubeananlyticsmini.OnClickListeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.youtubeananlyticsmini.CommentActivity;
import com.example.youtubeananlyticsmini.Constants;

public class ViewCommentButtonListener implements View.OnClickListener {

    private final Context context;
    private final String feedbackJson;

    public ViewCommentButtonListener(Context context, String feedback) {
        this.context = context;
        this.feedbackJson = feedback;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(Constants.COMMENT_INTENT_KEY, feedbackJson);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
