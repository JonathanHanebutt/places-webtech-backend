package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<PlaceEntry> allPlaces() {
        return service.getAll();
    }

    // POST /places -> neuen Ort anlegen
    @PostMapping("/places")
    public PlaceEntry createPlace(@RequestBody PlaceEntry place) {
        return service.create(place);
    }

    // POST /places/{id}/like -> Like zählen
    @PostMapping("/places/{id}/like")
    public PlaceEntry like(@PathVariable Long id) {
        return service.like(id);
    }

    // POST /places/{id}/dislike -> Dislike zählen
    @PostMapping("/places/{id}/dislike")
    public PlaceEntry dislike(@PathVariable Long id) {
        return service.dislike(id);
    }
}
