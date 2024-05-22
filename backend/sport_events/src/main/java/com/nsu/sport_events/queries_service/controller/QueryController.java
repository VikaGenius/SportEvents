package com.nsu.sport_events.queries_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.nsu.sport_events.queries_service.service.DatabaseService;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class QueryController {

    @Autowired
    private DatabaseService databaseService;

    @PostMapping("/sql-query")
    public ResponseEntity<?> executeSqlQuery(@RequestBody Map<String, String> payload) {
        String queryStr = payload.get("query");
        try {
            List<Object[]> resultList = databaseService.executeCustomQuery(queryStr);
            return ResponseEntity.ok(Map.of("results", resultList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error executing query: " + e.getMessage());
        }
    }
}
