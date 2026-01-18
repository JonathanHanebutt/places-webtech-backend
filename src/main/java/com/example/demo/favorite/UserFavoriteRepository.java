package com.example.demo.favorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Long> {

    List<UserFavorite> findByUserId(Long userId);

    List<UserFavorite> findByUserUsername(String username);

    Optional<UserFavorite> findByUserIdAndPlaceId(Long userId, Long placeId);

    Optional<UserFavorite> findByUserUsernameAndPlaceId(String username, Long placeId);

    boolean existsByUserUsernameAndPlaceId(String username, Long placeId);

    void deleteByUserUsernameAndPlaceId(String username, Long placeId);
}

