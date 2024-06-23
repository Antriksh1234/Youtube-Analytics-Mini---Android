package com.example.youtubeananlyticsmini;
public class Comment {
    private int likes;
    private String commenter;
    private String commentText;

    public Comment(int likes, String commenter, String commentText) {
        this.likes = likes;
        this.commenter = commenter;
        this.commentText = commentText;
    }


    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
