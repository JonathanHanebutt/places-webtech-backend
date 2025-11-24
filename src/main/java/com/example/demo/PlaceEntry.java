package com.example.demo;

public class PlaceEntry {

    private String name;
    private String description;
    private int rating;
    private int like;
    private int dislike;
    private String imageLink;

    public PlaceEntry() {
    }

    public PlaceEntry(String name, String description, int rating, int like, int dislike, String imageLink) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.like = like;
        this.dislike = dislike;
        this.imageLink = imageLink;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getRating() { return rating; }
    public int getLike() { return like; }
    public int getDislike() { return dislike; }
    public String getImageLink() { return imageLink; }
}
