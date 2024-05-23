package com.example.youtubeananlyticsmini;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AnalyticActivity extends AppCompatActivity {

    private VideoFeedback videoFeedback;


    //This is the video info card
    private TextView videoTitleTextView;
    private ImageView videoThumbnail;

    //This is the video stats card
    private TextView likesCountTextView, viewsCountTextView, commentsCountTextView;


    //This is the channel info card
    private ImageView channelThumbnail;
    private TextView channelNameTextView, channelSubsTextView;


    //This is the video sentiment card
    private PieChart pieChart;
    private TextView semtimentTextView;

    //This is new sentiment card
    private TextView sentiment1, sentiment2, sentiment3, sentiment4;
    private View sentimentBar1, sentimentBar2, sentimentBar3, sentimentBar4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytic);

        //Video Info
        videoTitleTextView = findViewById(R.id.video_title);
        videoThumbnail = findViewById(R.id.video_thumbnail);

        //Channel Info
        channelThumbnail = findViewById(R.id.channel_icon);
        channelNameTextView = findViewById(R.id.channel_name);
        channelSubsTextView = findViewById(R.id.channel_subs);

        //Video Sentiment
        semtimentTextView = findViewById(R.id.video_sentiment_text);
        pieChart = findViewById(R.id.sentiment_chart);

        //Video Stats
        likesCountTextView = findViewById(R.id.likes_count);
        viewsCountTextView = findViewById(R.id.views_count);
        commentsCountTextView = findViewById(R.id.comments_count);

        sentiment1 = findViewById(R.id.sentiment_1);
        sentiment2 = findViewById(R.id.sentiment_2);
        sentiment3 = findViewById(R.id.sentiment_3);
        sentiment4 = findViewById(R.id.sentiment_4);

        sentimentBar1 = findViewById(R.id.sentiment_bar_1);
        sentimentBar2 = findViewById(R.id.sentiment_bar_2);
        sentimentBar3 = findViewById(R.id.sentiment_bar_3);
        sentimentBar4 = findViewById(R.id.sentiment_bar_4);

        String feedbackJson = getIntent().getStringExtra(Constants.INTENT_KEY);
        videoFeedback = new Gson().fromJson(feedbackJson, VideoFeedback.class);

        fillUIWithStats();
    }

    private void fillUIWithStats() {
        fillVideoInfoCard();
        fillChannelInfoCard();
        fillSentimentCard();

    }

    private void fillVideoInfoCard() {
        //Fill video details
        videoTitleTextView.setText(videoFeedback.getTitle());
        Glide.with(getApplicationContext()).load(videoFeedback.getThumbnailURL()).into(videoThumbnail);

        //Fill video stats
        likesCountTextView.setText(formatNumbers(videoFeedback.getLikes()));
        viewsCountTextView.setText(formatNumbers(videoFeedback.getViews()));
        commentsCountTextView.setText(formatNumbers(videoFeedback.getNumberOfComments()));
    }

    private void fillChannelInfoCard() {
        //Fill channel info
        channelNameTextView.setText(videoFeedback.getChannel());
        channelSubsTextView.setText(formatNumbers(videoFeedback.getChannelSubs()) + " Subscribers");
        Glide.with(getApplicationContext()).load(videoFeedback.getChannelThumbnail()).into(channelThumbnail);
    }

    private void fillSentimentCard() {
        //Fil sentiments
        semtimentTextView.setText(videoFeedback.getSentiment());
        Map<String, Integer> sentimentStats = videoFeedback.getSentimentStats();
        List<PieEntry> entries = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : sentimentStats.entrySet()) {
            String sentiment = entry.getKey();
            int count = entry.getValue();
            entries.add(new PieEntry(count, sentiment));
            System.out.println(sentiment + " " + count);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(Color.parseColor("#15bd15"), Color.parseColor("#ed3e4f"), Color.parseColor("#148f70"), Color.parseColor("#ebb513"));
        dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
        dataSet.setSliceSpace(8f);
        dataSet.setDrawValues(false);

        PieData data = new PieData(dataSet);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setHoleColor(Color.parseColor("#272323"));
        pieChart.invalidate(); // Refresh the chart
    }

    void fillSentimentCardTwo() {
        Map<String, Integer> sentiment =  videoFeedback.getSentimentStats();
        if (sentiment == null) {
            return;
        }

        Sentiment []sentiments = new Sentiment[4];
        sentiments[0] = new Sentiment("Positive", sentiment.getOrDefault("POSITIVE", 0));
        sentiments[1] = new Sentiment("Negative", sentiment.getOrDefault("NEGATIVE", 0));
        sentiments[2] = new Sentiment("Mixed", sentiment.getOrDefault("MIXED", 0));
        sentiments[3] = new Sentiment("Neutral", sentiment.getOrDefault("NEUTRAL", 0));

        Arrays.sort(sentiments);

        float maxWidth = sentimentBar1.getWidth();

    }

    int getMax(int a, int b, int c, int d) {
        return Math.max(Math.max(a, b), Math.max(c, d));
    }

    private String formatNumbers(int subscriberCount) {
        String formattedCount;

        if (subscriberCount < 1000) {
            formattedCount = String.valueOf(subscriberCount);
        } else if (subscriberCount < 1000000) {
            formattedCount = String.format("%.1fK", subscriberCount / 1000.0);
        } else {
            formattedCount = String.format("%.1fM", subscriberCount / 1000000.0);
        }

        return formattedCount;
    }
}