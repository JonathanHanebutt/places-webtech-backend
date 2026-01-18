package com.example.demo.favorite;

import com.example.demo.PlaceEntry;
import com.example.demo.PlaceEntryDTO;
import com.example.demo.PlaceEntryRepository;
import com.example.demo.auth.AppUser;
import com.example.demo.auth.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final UserFavoriteRepository favoriteRepository;
    private final PlaceEntryRepository placeRepository;
    private final AppUserRepository userRepository;

    public FavoriteService(UserFavoriteRepository favoriteRepository,
                           PlaceEntryRepository placeRepository,
                           AppUserRepository userRepository) {
        this.favoriteRepository = favoriteRepository;
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PlaceEntryDTO addFavorite(Long placeId, String username) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        PlaceEntry place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));

        // Prüfen ob bereits favorisiert
        if (favoriteRepository.existsByUserUsernameAndPlaceId(username, placeId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Place already in favorites");
        }

        // Favorit speichern
        UserFavorite favorite = new UserFavorite(user, place);
        favoriteRepository.save(favorite);

        // Like-Count erhöhen
        place.setLikeCount(place.getLikeCount() + 1);
        PlaceEntry savedPlace = placeRepository.save(place);

        return new PlaceEntryDTO(savedPlace);
    }

    @Transactional
    public PlaceEntryDTO removeFavorite(Long placeId, String username) {
        PlaceEntry place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));

        UserFavorite favorite = favoriteRepository.findByUserUsernameAndPlaceId(username, placeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Favorite not found"));

        favoriteRepository.delete(favorite);

        // Like-Count verringern (nicht unter 0)
        if (place.getLikeCount() > 0) {
            place.setLikeCount(place.getLikeCount() - 1);
        }
        PlaceEntry savedPlace = placeRepository.save(place);

        return new PlaceEntryDTO(savedPlace);
    }

    public List<PlaceEntryDTO> getUserFavorites(String username) {
        return favoriteRepository.findByUserUsername(username)
                .stream()
                .map(fav -> new PlaceEntryDTO(fav.getPlace()))
                .collect(Collectors.toList());
    }

    public List<Long> getUserFavoriteIds(String username) {
        return favoriteRepository.findByUserUsername(username)
                .stream()
                .map(fav -> fav.getPlace().getId())
                .collect(Collectors.toList());
    }

    public boolean isFavorite(Long placeId, String username) {
        return favoriteRepository.existsByUserUsernameAndPlaceId(username, placeId);
    }
}

