package com.example.demo;

import com.example.demo.auth.AppUser;
import com.example.demo.auth.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PlaceEntryServiceTest {

    @Autowired
    private PlaceEntryService placeEntryService;

    @Autowired
    private PlaceEntryRepository placeEntryRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    private AppUser testUser;

    @BeforeEach
    void setUp() {
        // Erstelle einen Test-User
        testUser = new AppUser();
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPasswordHash("hashedpassword");
        testUser = appUserRepository.save(testUser);
    }

    @Test
    void testCreatePlace() {
        // Given
        PlaceEntry place = new PlaceEntry();
        place.setName("Test Ort");
        place.setDescription("Ein schöner Testort");
        place.setRating(5);

        // When
        PlaceEntryDTO result = placeEntryService.create(place, testUser.getUsername());

        // Then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Test Ort", result.getName());
        assertEquals("Ein schöner Testort", result.getDescription());
        assertEquals(5, result.getRating());
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testGetAllPlaces() {
        // Given
        PlaceEntry place1 = new PlaceEntry();
        place1.setName("Ort 1");
        place1.setDescription("Beschreibung 1");
        place1.setUser(testUser);
        placeEntryRepository.save(place1);

        PlaceEntry place2 = new PlaceEntry();
        place2.setName("Ort 2");
        place2.setDescription("Beschreibung 2");
        place2.setUser(testUser);
        placeEntryRepository.save(place2);

        // When
        var places = placeEntryService.getAll();

        // Then
        assertTrue(places.size() >= 2);
    }

    @Test
    void testLikePlace() {
        // Given
        PlaceEntry place = new PlaceEntry();
        place.setName("Like Test Ort");
        place.setDescription("Beschreibung");
        place.setLikeCount(0);
        place.setUser(testUser);
        place = placeEntryRepository.save(place);

        // When
        PlaceEntryDTO result = placeEntryService.like(place.getId());

        // Then
        assertEquals(1, result.getLikeCount());
    }

    @Test
    void testDislikePlace() {
        // Given
        PlaceEntry place = new PlaceEntry();
        place.setName("Dislike Test Ort");
        place.setDescription("Beschreibung");
        place.setDislikeCount(0);
        place.setUser(testUser);
        place = placeEntryRepository.save(place);

        // When
        PlaceEntryDTO result = placeEntryService.dislike(place.getId());

        // Then
        assertEquals(1, result.getDislikeCount());
    }
}

