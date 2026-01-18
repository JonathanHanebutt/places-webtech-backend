package com.example.demo.favorite;

import com.example.demo.PlaceEntry;
import com.example.demo.PlaceEntryDTO;
import com.example.demo.PlaceEntryRepository;
import com.example.demo.auth.AppUser;
import com.example.demo.auth.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FavoriteServiceTest {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserFavoriteRepository favoriteRepository;

    @Autowired
    private PlaceEntryRepository placeRepository;

    @Autowired
    private AppUserRepository userRepository;

    private AppUser testUser;
    private PlaceEntry testPlace;

    @BeforeEach
    void setUp() {
        // Erstelle Test-User
        testUser = new AppUser();
        testUser.setUsername("favoriteuser");
        testUser.setEmail("favorite@example.com");
        testUser.setPasswordHash("hashedpassword");
        testUser = userRepository.save(testUser);

        // Erstelle Test-Place
        testPlace = new PlaceEntry();
        testPlace.setName("Favorite Test Ort");
        testPlace.setDescription("Ein Ort zum Favorisieren");
        testPlace.setLikeCount(0);
        testPlace.setUser(testUser);
        testPlace = placeRepository.save(testPlace);
    }

    @Test
    void testAddFavorite() {
        // When
        PlaceEntryDTO result = favoriteService.addFavorite(testPlace.getId(), testUser.getUsername());

        // Then
        assertNotNull(result);
        assertEquals(1, result.getLikeCount());
        assertTrue(favoriteService.isFavorite(testPlace.getId(), testUser.getUsername()));
    }

    @Test
    void testAddFavoriteDuplicate() {
        // Given
        favoriteService.addFavorite(testPlace.getId(), testUser.getUsername());

        // When & Then - sollte Exception werfen
        assertThrows(ResponseStatusException.class, () -> {
            favoriteService.addFavorite(testPlace.getId(), testUser.getUsername());
        });
    }

    @Test
    void testRemoveFavorite() {
        // Given
        favoriteService.addFavorite(testPlace.getId(), testUser.getUsername());

        // When
        PlaceEntryDTO result = favoriteService.removeFavorite(testPlace.getId(), testUser.getUsername());

        // Then
        assertEquals(0, result.getLikeCount());
        assertFalse(favoriteService.isFavorite(testPlace.getId(), testUser.getUsername()));
    }

    @Test
    void testGetUserFavorites() {
        // Given
        PlaceEntry place2 = new PlaceEntry();
        place2.setName("Zweiter Ort");
        place2.setDescription("Noch ein Ort");
        place2.setLikeCount(0);
        place2.setUser(testUser);
        place2 = placeRepository.save(place2);

        favoriteService.addFavorite(testPlace.getId(), testUser.getUsername());
        favoriteService.addFavorite(place2.getId(), testUser.getUsername());

        // When
        List<PlaceEntryDTO> favorites = favoriteService.getUserFavorites(testUser.getUsername());

        // Then
        assertEquals(2, favorites.size());
    }

    @Test
    void testGetUserFavoriteIds() {
        // Given
        favoriteService.addFavorite(testPlace.getId(), testUser.getUsername());

        // When
        List<Long> ids = favoriteService.getUserFavoriteIds(testUser.getUsername());

        // Then
        assertEquals(1, ids.size());
        assertTrue(ids.contains(testPlace.getId()));
    }

    @Test
    void testIsFavorite() {
        // Given - kein Favorit
        assertFalse(favoriteService.isFavorite(testPlace.getId(), testUser.getUsername()));

        // When
        favoriteService.addFavorite(testPlace.getId(), testUser.getUsername());

        // Then
        assertTrue(favoriteService.isFavorite(testPlace.getId(), testUser.getUsername()));
    }
}

