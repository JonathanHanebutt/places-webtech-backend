package com.example.demo;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PlaceEntryController {

    @GetMapping("/places")
    public List<PlaceEntry> allPlaces() {
        return List.of(
                new PlaceEntry(
                        "HTW Berlin",
                        "Campus Wilhelminenhof an der Spree",
                        5,
                        42,
                        1,
                        "/images/campus-wilhelminenhof.jpg"
                ),
                new PlaceEntry(
                        "Tempelhofer Feld",
                        "Ehemaliger Flughafen, heute Freizeitfläche",
                        4,
                        30,
                        2,
                        "/images/tempelhoferfeld.jpg"
                ),
                new PlaceEntry(
                        "Tiergarten",
                        "Großer Park im Herzen von Berlin",
                        5,
                        60,
                        3,
                        "/images/Tiergarten.jpeg"
                )
        );
    }
}
