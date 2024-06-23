package com.example.youtubeananlyticsmini;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.youtubeananlyticsmini.OnClickListeners.PlayVideoButtonListener;
import com.example.youtubeananlyticsmini.OnClickListeners.ViewCommentButtonListener;
import com.example.youtubeananlyticsmini.adapters.SentimentAdapter;
import com.example.youtubeananlyticsmini.adapters.SentimentAdapter2;
import com.github.mikephil.charting.charts.PieChart;
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

    //Get these from intent
    private VideoFeedback videoFeedback;
    private String videoURL;


    //This is the video info card
    private TextView videoTitleTextView;
    private ImageView videoThumbnail;

    //This is the video stats card
    private TextView likesCountTextView, viewsCountTextView, commentsCountTextView;

    private Button playVideoButton, viewComments;

    //This is the channel info card
    private ImageView channelThumbnail;
    private TextView channelNameTextView, channelSubsTextView;


    //This is the video sentiment card
    private PieChart pieChart;
    private TextView semtimentTextView;
    private RecyclerView semtimentRecyclerView;

    //This is new sentiment card
    private RecyclerView sentimentRecyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytic);

        //Video Info
        videoTitleTextView = findViewById(R.id.video_title);
        videoThumbnail = findViewById(R.id.video_thumbnail);

        playVideoButton = findViewById(R.id.watch_button);
        viewComments = findViewById(R.id.view_comments);

        //Channel Info
        channelThumbnail = findViewById(R.id.channel_icon);
        channelNameTextView = findViewById(R.id.channel_name);
        channelSubsTextView = findViewById(R.id.channel_subs);

        //Video Sentiment
        semtimentTextView = findViewById(R.id.video_sentiment_text);
        pieChart = findViewById(R.id.sentiment_chart);
        semtimentRecyclerView = findViewById(R.id.sentiment_recycleview);

        //Video Stats
        likesCountTextView = findViewById(R.id.likes_count);
        viewsCountTextView = findViewById(R.id.views_count);
        commentsCountTextView = findViewById(R.id.comments_count);

        //Video Sentiment card 2
        sentimentRecyclerView2 = findViewById(R.id.sentiment_recyclerview_bar_chart);

        String feedbackJson = getIntent().getStringExtra(Constants.INTENT_KEY);
        videoFeedback = new Gson().fromJson(feedbackJson, VideoFeedback.class);
        videoURL = getIntent().getStringExtra(Constants.VIDEO_LINK);

        playVideoButton.setOnClickListener(new PlayVideoButtonListener(getApplicationContext(), videoURL));
        viewComments.setOnClickListener(new ViewCommentButtonListener(getApplicationContext(), feedbackJson));

        fillUIWithStats();
    }

    private void fillUIWithStats() {
        fillVideoInfoCard();
        fillChannelInfoCard();
        fillSentimentCard();
        fillSentimentCardTwo();
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

        Map<String, Integer> sentimentMap =  videoFeedback.getSentimentStats();
        if (sentimentMap == null) {
            return;
        }

        Sentiment []sentiments = new Sentiment[4];
        sentiments[0] = new Sentiment("Positive: " + sentimentMap.getOrDefault("POSITIVE", 0), sentimentMap.getOrDefault("POSITIVE", 0), "#15bd15", R.drawable.positive_circle);
        sentiments[1] = new Sentiment("Negative: " + sentimentMap.getOrDefault("NEGATIVE", 0), sentimentMap.getOrDefault("NEGATIVE", 0), "#ed3e4f", R.drawable.negative_circle);
        sentiments[2] = new Sentiment("Mixed: " + sentimentMap.getOrDefault("MIXED", 0), sentimentMap.getOrDefault("MIXED", 0), "#ebb513", R.drawable.mixed_circle);
        sentiments[3] = new Sentiment("Neutral: " + sentimentMap.getOrDefault("NEUTRAL", 0), sentimentMap.getOrDefault("NEUTRAL", 0), "#148f70", R.drawable.neutral_circle);

        Arrays.sort(sentiments);
        List<PieEntry> entries = new ArrayList<>();

        for (Sentiment sentiment : sentiments) {
            int count = sentiment.getScore();
            entries.add(new PieEntry(count, ""));
            System.out.println(sentiment + " " + count);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(Color.parseColor(sentiments[0].getColor()), Color.parseColor(sentiments[1].getColor()), Color.parseColor(sentiments[2].getColor()), Color.parseColor(sentiments[3].getColor()));
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
        pieChart.setHoleRadius(60);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setHoleColor(Color.parseColor("#272323"));
        pieChart.invalidate(); // Refresh the chart

        SentimentAdapter adapter = new SentimentAdapter(getApplicationContext(), sentiments);
        semtimentRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        semtimentRecyclerView.setAdapter(adapter);
    }

    void fillSentimentCardTwo() {
        Map<String, Integer> sentimentMap =  videoFeedback.getSentimentStats();
        if (sentimentMap == null) {
            return;
        }

        Sentiment []sentiments = new Sentiment[4];
        sentiments[0] = new Sentiment("Positive", sentimentMap.getOrDefault("POSITIVE", 0), "#15bd15", R.drawable.positive_circle);
        sentiments[1] = new Sentiment("Negative", sentimentMap.getOrDefault("NEGATIVE", 0), "#ed3e4f", R.drawable.negative_circle);
        sentiments[2] = new Sentiment("Mixed", sentimentMap.getOrDefault("MIXED", 0), "#ebb513", R.drawable.mixed_circle);
        sentiments[3] = new Sentiment("Neutral", sentimentMap.getOrDefault("NEUTRAL", 0), "#148f70", R.drawable.neutral_circle);

        Arrays.sort(sentiments);

        SentimentAdapter2 adapter = new SentimentAdapter2(getApplicationContext(), sentiments);
        sentimentRecyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        sentimentRecyclerView2.setAdapter(adapter);
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