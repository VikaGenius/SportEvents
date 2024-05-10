package com.nsu.sport_events.playground_service.repository;

import com.nsu.sport_events.playground_service.model.Playground;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaygroundRepository extends JpaRepository<Playground, Long> {
    @SuppressWarnings("null")
    Optional<Playground> findById(Long id);

    List<Playground> findByName(String name);

    List<Playground> findByAddress(String address);

    @SuppressWarnings("null")
    void deleteById(Long id);
}

