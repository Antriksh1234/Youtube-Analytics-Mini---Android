package com.example.youtubeananlyticsmini.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeananlyticsmini.models.Comment;
import com.example.youtubeananlyticsmini.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    private List<Comment> commentList;
    private Context context;

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_recyclerview_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        char firstLetter = Character.toUpperCase(commentList.get(position).getCommenter().charAt(1));
        holder.commenterFirstLetterTextView.setText("" + firstLetter);
        holder.nameTextView.setText(commentList.get(position).getCommenter());
        holder.commentTextView.setText(commentList.get(position).getCommentText());
        holder.likesTextView.setText("" + commentList.get(position).getLikes());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}

class CommentViewHolder extends RecyclerView.ViewHolder {
    TextView nameTextView, commentTextView, commenterFirstLetterTextView, likesTextView;
    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.comment_name);
        commentTextView = itemView.findViewById(R.id.comment_text);
        commenterFirstLetterTextView = itemView.findViewById(R.id.comment_first_letter);
        likesTextView = itemView.findViewById(R.id.likes_of_comment);
    }
}
