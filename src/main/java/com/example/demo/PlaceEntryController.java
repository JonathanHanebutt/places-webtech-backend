package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PlaceEntryController {

    private final PlaceEntryService service;

    @Autowired
    public PlaceEntryController(PlaceEntryService service) {
        this.service = service;
    }

    // GET /places -> alle Einträge aus DB
    @GetMapping("/places")
    public List<PlaceEntryDTO> allPlaces() {
        return service.getAll();
    }

    // POST /places -> neuen Ort anlegen (requires authentication)
    @PostMapping("/places")
    public PlaceEntryDTO createPlace(@RequestBody PlaceEntry place,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        return service.create(place, userDetails.getUsername());
    }

    // POST /places/{id}/like -> Like zählen
    @PostMapping("/places/{id}/like")
    public PlaceEntryDTO like(@PathVariable Long id) {
        return service.like(id);
    }

    // POST /places/{id}/dislike -> Dislike zählen
    @PostMapping("/places/{id}/dislike")
    public PlaceEntryDTO dislike(@PathVariable Long id) {
        return service.dislike(id);
    }
}
