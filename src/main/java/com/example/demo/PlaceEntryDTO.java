package com.example.demo;

public class PlaceEntryDTO {

    private Long id;
    private String name;
    private String description;
    private int rating;
    private int likeCount;
    private int dislikeCount;
    private String imageLink;
    private String username;

    public PlaceEntryDTO() {
    }

    public PlaceEntryDTO(PlaceEntry place) {
        this.id = place.getId();
        this.name = place.getName();
        this.description = place.getDescription();
        this.rating = place.getRating();
        this.likeCount = place.getLikeCount();
        this.dislikeCount = place.getDislikeCount();
        this.imageLink = place.getImageLink();
        this.username = place.getUser() != null ? place.getUser().getUsername() : null;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getRating() { return rating; }
    public int getLikeCount() { return likeCount; }
    public int getDislikeCount() { return dislikeCount; }
    public String getImageLink() { return imageLink; }
    public String getUsername() { return username; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setRating(int rating) { this.rating = rating; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public void setDislikeCount(int dislikeCount) { this.dislikeCount = dislikeCount; }
    public void setImageLink(String imageLink) { this.imageLink = imageLink; }
    public void setUsername(String username) { this.username = username; }
}

