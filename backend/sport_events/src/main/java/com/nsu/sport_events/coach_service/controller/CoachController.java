package com.nsu.sport_events.coach_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nsu.sport_events.coach_service.model.Coach;
import com.nsu.sport_events.coach_service.repository.CoachRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/coaches")
public class CoachController {

    @Autowired
    private CoachRepository coachRepository;

    @GetMapping
    public ResponseEntity<List<Coach>> getAllPlaygrounds() {
        List<Coach> coaches = coachRepository.findAll();
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addCoach(@RequestBody Coach coach) {
        coachRepository.save(coach);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coach> getCoach(@PathVariable("id") Long id) {
        Coach coach = coachRepository.findById(id).get();
        return new ResponseEntity<>(coach, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoach(@PathVariable("id") Long id) {
        coachRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
