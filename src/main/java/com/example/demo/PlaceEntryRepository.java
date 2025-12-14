package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceEntryRepository extends CrudRepository<PlaceEntry, Long> {
    boolean existsByName(String name);
}
