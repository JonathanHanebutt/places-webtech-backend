package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PlaceEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int rating;
    private int likeCount;
    private int dislikeCount;
    private String imageLink;

    public PlaceEntry() {
    }

    public PlaceEntry(
            String name,
            String description,
            int rating,
            int likeCount,
            int dislikeCount,
            String imageLink
    ) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.imageLink = imageLink;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getRating() { return rating; }
    public int getLikeCount() { return likeCount; }
    public int getDislikeCount() { return dislikeCount; }
    public String getImageLink() { return imageLink; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setRating(int rating) { this.rating = rating; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public void setDislikeCount(int dislikeCount) { this.dislikeCount = dislikeCount; }
    public void setImageLink(String imageLink) { this.imageLink = imageLink; }
}
