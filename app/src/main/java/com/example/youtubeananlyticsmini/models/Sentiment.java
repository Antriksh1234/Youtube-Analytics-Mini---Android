package com.example.youtubeananlyticsmini.models;

public class Sentiment implements Comparable<Sentiment> {
    private String name;
    private int score;
    private String color;
    private int backgroundCircle;

    public Sentiment(String name, int score, String color, int backgroundCircle) {
        this.name = name;
        this.score = score;
        this.color = color;
        this.backgroundCircle = backgroundCircle;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Sentiment s) {
        if (this.score > s.getScore()) {
            return -1;
        } else if (this.score < s.getScore()) {
            return 1;
        }

        return  this.name.compareTo(s.getName());
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getBackgroundCircle() {
        return backgroundCircle;
    }

    public void setBackgroundCircle(int backgroundCircle) {
        this.backgroundCircle = backgroundCircle;
    }
}
