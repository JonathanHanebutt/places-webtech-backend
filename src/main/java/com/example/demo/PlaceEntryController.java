package com.example.demo;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceEntryController {

    @GetMapping("/places")
    public String allPlaces() {
        return "All places";
    }
}
