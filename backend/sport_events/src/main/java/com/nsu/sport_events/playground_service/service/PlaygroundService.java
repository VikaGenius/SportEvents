package com.nsu.sport_events.playground_service.service;

import com.nsu.sport_events.playground_service.model.Playground;
import com.nsu.sport_events.playground_service.repository.PlaygroundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaygroundService {

    @Autowired
    private PlaygroundRepository playgroundRepository;

    public void addPlayground(Playground playground) {
        playgroundRepository.save(playground);
    }

    public Playground getPlaygroundById(Long id) {
        return playgroundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playground not found"));
    }

    public List<Playground> getAllPlaygrounds() {
        return playgroundRepository.findAll();
    }

    public void updatePlayground(Long id, Playground updatedPlayground) {
        Playground playground = playgroundRepository.findById(id).orElseThrow(() -> new RuntimeException("Playground not found"));

        playground.setName(updatedPlayground.getName());
        playground.setAddress(updatedPlayground.getAddress());
        playground.setNumberOfSeats(updatedPlayground.getNumberOfSeats());

        playgroundRepository.save(playground);
    }

    public void deletePlayground(Long id) {
        playgroundRepository.deleteById(id);
    }

}
