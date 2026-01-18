package com.example.demo.favorite;

import com.example.demo.PlaceEntryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // POST /api/favorites/{placeId} - Ort zu Favoriten hinzufügen
    @PostMapping("/{placeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceEntryDTO addFavorite(
            @PathVariable Long placeId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return favoriteService.addFavorite(placeId, userDetails.getUsername());
    }

    // DELETE /api/favorites/{placeId} - Ort aus Favoriten entfernen
    @DeleteMapping("/{placeId}")
    @ResponseStatus(HttpStatus.OK)
    public PlaceEntryDTO removeFavorite(
            @PathVariable Long placeId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return favoriteService.removeFavorite(placeId, userDetails.getUsername());
    }

    // GET /api/favorites - Alle Favoriten des Users abrufen
    @GetMapping
    public List<PlaceEntryDTO> getUserFavorites(
            @AuthenticationPrincipal UserDetails userDetails) {
        return favoriteService.getUserFavorites(userDetails.getUsername());
    }

    // GET /api/favorites/ids - Nur IDs der Favoriten abrufen (für Frontend-Markierung)
    @GetMapping("/ids")
    public List<Long> getUserFavoriteIds(
            @AuthenticationPrincipal UserDetails userDetails) {
        return favoriteService.getUserFavoriteIds(userDetails.getUsername());
    }

    // GET /api/favorites/check/{placeId} - Prüfen ob Ort favorisiert ist
    @GetMapping("/check/{placeId}")
    public FavoriteCheckResponse checkFavorite(
            @PathVariable Long placeId,
            @AuthenticationPrincipal UserDetails userDetails) {
        boolean isFavorite = favoriteService.isFavorite(placeId, userDetails.getUsername());
        return new FavoriteCheckResponse(isFavorite);
    }

    public static class FavoriteCheckResponse {
        public boolean isFavorite;

        public FavoriteCheckResponse(boolean isFavorite) {
            this.isFavorite = isFavorite;
        }
    }
}

