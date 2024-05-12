package com.nsu.sport_events.coach_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsu.sport_events.coach_service.model.Coach;

import java.util.Optional;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    Optional<Coach> findById(Long id);

    void deleteById(Long id);
    
    List<Coach> findAll();
}

