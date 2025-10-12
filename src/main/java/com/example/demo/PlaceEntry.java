package com.example.demo;

public class PlaceEntry {

    String name;
    String description;
    int rating;
    int like;
    int dislike;
    String imageLink;

    PlaceEntry(){
    }

    public PlaceEntry(String name, String description, int rating, int like, int dislike, String imageLink) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.like = like;
        this.dislike = dislike;
        this.imageLink = imageLink;
    }

}
