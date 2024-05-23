package com.example.youtubeananlyticsmini;

public class Sentiment implements Comparable<Sentiment> {
    private String name;
    private int score;

    public Sentiment(String name, int score) {
        this.name = name;
        this.score = score;
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
        }

        return  this.name.compareTo(s.getName());
    }
}
