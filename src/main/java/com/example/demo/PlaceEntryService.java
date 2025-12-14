package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceEntryService {

    private final PlaceEntryRepository repo;

    @Autowired
    public PlaceEntryService(PlaceEntryRepository repo) {
        this.repo = repo;
    }

    public List<PlaceEntry> getAll() {
        List<PlaceEntry> result = new ArrayList<>();
        repo.findAll().forEach(result::add);
        return result;
    }

    public PlaceEntry create(PlaceEntry place) {
        // falls anfänglich 0 Likes/Dislikes
        if (place.getLikeCount() == 0) place.setLikeCount(0);
        if (place.getDislikeCount() == 0) place.setDislikeCount(0);
        return repo.save(place);
    }

    public PlaceEntry like(Long id) {
        PlaceEntry p = repo.findById(id).orElseThrow();
        p.setLikeCount(p.getLikeCount() + 1);
        return repo.save(p);
    }

    public PlaceEntry dislike(Long id) {
        PlaceEntry p = repo.findById(id).orElseThrow();
        p.setDislikeCount(p.getDislikeCount() + 1);
        return repo.save(p);
    }
}
