package com.nsu.sport_events.coach_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Coaches")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long cId;

    @Column(nullable = false, name = "full_name")
    private String fullName;
    

    public Long getId() {
        return cId;
    }

    public void setId(Long id) {
        this.cId = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
