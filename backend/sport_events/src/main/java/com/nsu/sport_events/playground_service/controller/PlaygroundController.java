package com.nsu.sport_events.playground_service.controller;

import com.nsu.sport_events.playground_service.model.Playground;
import com.nsu.sport_events.playground_service.service.PlaygroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/playgrounds")
public class PlaygroundController {

    @Autowired
    private PlaygroundService playgroundService;

    @GetMapping
    public ResponseEntity<List<Playground>> getAllPlaygrounds() {
        List<Playground> playgrounds = playgroundService.getAllPlaygrounds();
        return new ResponseEntity<>(playgrounds, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addPlayground(@RequestBody Playground playground) {
        playgroundService.addPlayground(playground);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playground> getPlayground(@PathVariable("p_id") Long id) {
        Playground playground = playgroundService.getPlaygroundById(id);
        return new ResponseEntity<>(playground, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePlayground(@PathVariable("p_id") Long id, @RequestBody Playground updatedPlayground) {
        playgroundService.updatePlayground(id, updatedPlayground);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayground(@PathVariable("p_id") Long id) {
        playgroundService.deletePlayground(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
