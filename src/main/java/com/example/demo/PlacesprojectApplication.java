package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class PlacesprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlacesprojectApplication.class, args);
    }

    // Wird beim Start einmal ausgeführt
    @Bean
    CommandLineRunner initDatabase(PlaceEntryRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.saveAll(List.of(
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
                ));
            }
        };
    }
}
