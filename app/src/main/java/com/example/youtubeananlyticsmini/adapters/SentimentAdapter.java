package com.example.youtubeananlyticsmini.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeananlyticsmini.R;
import com.example.youtubeananlyticsmini.models.Sentiment;

public class SentimentAdapter extends RecyclerView.Adapter<SentimentHolder>{

    Context context;
    Sentiment [] sentimentList;

    public SentimentAdapter(Context context, Sentiment [] sentimentList) {
        this.context = context;
        this.sentimentList = sentimentList;
    }

    @NonNull
    @Override
    public SentimentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sentiment_item_recycleview, parent, false);
        return new SentimentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SentimentHolder holder, int position) {
        holder.textView.setText(sentimentList[position].getName());
        holder.view.setBackgroundResource(sentimentList[position].getBackgroundCircle());
    }

    @Override
    public int getItemCount() {
        return sentimentList.length;
    }
}

class SentimentHolder extends RecyclerView.ViewHolder {
    TextView textView;
    View view;
    public SentimentHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.sentiment_text);
        view = itemView.findViewById(R.id.sentiment_color);
    }
}
