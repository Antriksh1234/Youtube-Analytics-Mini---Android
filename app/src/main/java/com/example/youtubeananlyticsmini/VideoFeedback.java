package com.example.youtubeananlyticsmini;

import java.util.Map;

public class VideoFeedback {

    //This is the video sentiment
    private String sentiment, summary;
    private Map<String, Integer> sentimentStats;

    //This is the video stats
    private int views, likes, numberOfComments;

    //This is the channel info
    private String channel, channelThumbnail;

    //This is video info
    private String title, thumbnailURL;
    private int channelSubs;

    public String getChannelThumbnail() {
        return channelThumbnail;
    }

    public void setChannelThumbnail(String channelThumbnail) {
        this.channelThumbnail = channelThumbnail;
    }

    public Map<String, Integer> getSentimentStats() {
        return sentimentStats;
    }

    public void setSentimentStats(Map<String, Integer> sentimentStats) {
        this.sentimentStats = sentimentStats;
    }

    public String getChannel() {
        return channel;
    }

    public String getSentiment() {
        return sentiment;
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public int getViews() {
        return views;
    }

    public int getLikes() {
        return likes;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public int getChannelSubs() {
        return channelSubs;
    }

    public void setChannelSubs(int channelSubs) {
        this.channelSubs = channelSubs;
    }

    public VideoFeedback(String sentiment, String summary, String channel, String title, String thumbnailURL, int views, int likes, int numberOfComments, Map<String, Integer> sentimentStats, String channelThumbnail, int channelSubs) {
        this.sentiment = sentiment;
        this.summary = summary;
        this.channel = channel;
        this.title = title;
        this.thumbnailURL = thumbnailURL;
        this.views = views;
        this.likes = likes;
        this.numberOfComments = numberOfComments;
        this.sentimentStats = sentimentStats;
        this.channelThumbnail = channelThumbnail;
        this.channelSubs = channelSubs;
    }
}
