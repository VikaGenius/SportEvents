package com.nsu.sport_events.queries_service.service;

import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class DatabaseService {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Transactional() 
    public List<Object[]> executeCustomQuery(String query) {
        return entityManager.createNativeQuery(query).getResultList();
    }
}
