package com.nsu.sport_events.playground_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Playgrounds", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "name", "address" })
})
public class Playground {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private Long pId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, name = "number_of_seats")
    private String numberOfSeats;

    public Long getId() {
        return pId;
    }

    public void setId(Long id) {
        this.pId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(String numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

}