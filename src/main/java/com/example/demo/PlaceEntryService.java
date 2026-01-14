package com.example.demo;

import com.example.demo.auth.AppUser;
import com.example.demo.auth.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceEntryService {

    private final PlaceEntryRepository repo;
    private final AppUserRepository userRepository;

    @Autowired
    public PlaceEntryService(PlaceEntryRepository repo, AppUserRepository userRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
    }

    public List<PlaceEntryDTO> getAll() {
        List<PlaceEntry> places = new ArrayList<>();
        repo.findAll().forEach(places::add);
        return places.stream()
                .map(PlaceEntryDTO::new)
                .collect(Collectors.toList());
    }

    public PlaceEntryDTO create(PlaceEntry place, String username) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // falls anfänglich 0 Likes/Dislikes
        if (place.getLikeCount() == 0) place.setLikeCount(0);
        if (place.getDislikeCount() == 0) place.setDislikeCount(0);
        place.setUser(user);

        PlaceEntry savedPlace = repo.save(place);
        return new PlaceEntryDTO(savedPlace);
    }

    public PlaceEntryDTO like(Long id) {
        PlaceEntry p = repo.findById(id).orElseThrow();
        p.setLikeCount(p.getLikeCount() + 1);
        return new PlaceEntryDTO(repo.save(p));
    }

    public PlaceEntryDTO dislike(Long id) {
        PlaceEntry p = repo.findById(id).orElseThrow();
        p.setDislikeCount(p.getDislikeCount() + 1);
        return new PlaceEntryDTO(repo.save(p));
    }
}
