package com.airline.reservation.audit;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "flight_search_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSearchLog {

    @Id
    private String id;

    private Long userId;       // null for anonymous
    private String origin;
    private String destination;
    private LocalDate travelDate;
    private int resultsCount;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
