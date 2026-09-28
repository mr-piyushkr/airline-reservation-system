package com.airline.reservation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "aircraft")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String registrationNumber;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private int economySeats;

    @Column(nullable = false)
    private int businessSeats;

    @Column(nullable = false)
    private int firstClassSeats;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;
}
