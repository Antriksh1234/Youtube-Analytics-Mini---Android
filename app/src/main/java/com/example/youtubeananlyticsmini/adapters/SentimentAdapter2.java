package com.example.youtubeananlyticsmini.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeananlyticsmini.R;
import com.example.youtubeananlyticsmini.Sentiment;

public class SentimentAdapter2 extends RecyclerView.Adapter<SentimentHolder2>{
    Context context;
    Sentiment [] sentimentList;
    private final int maxWidth = 900;

    public SentimentAdapter2(Context context, Sentiment [] sentimentList) {
        this.context = context;
        this.sentimentList = sentimentList;
    }

    @NonNull
    @Override
    public SentimentHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sentiment_item_recyclerview_2, parent, false);
        return new SentimentHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SentimentHolder2 holder, int position) {
        holder.textView.setText(sentimentList[position].getName());
        holder.view.setBackgroundResource(sentimentList[position].getBackgroundCircle());
        holder.view.getLayoutParams().width = getBarWidth(sentimentList[position].getScore());
    }

    private int getBarWidth(int score) {
        if (sentimentList[0].getScore() == 0) {
            return 20;
        }

        return Math.max(20, (int) (maxWidth * score / (float) sentimentList[0].getScore()));
    }

    @Override
    public int getItemCount() {
        return sentimentList.length;
    }
}

class SentimentHolder2 extends RecyclerView.ViewHolder {

    TextView textView = itemView.findViewById(R.id.sentiment_text_bar_chart);
    View view = itemView.findViewById(R.id.sentiment_bar);

    public SentimentHolder2(@NonNull View itemView) {
        super(itemView);
    }
}
