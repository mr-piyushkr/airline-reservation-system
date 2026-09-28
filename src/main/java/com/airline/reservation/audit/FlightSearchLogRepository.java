package com.airline.reservation.audit;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightSearchLogRepository extends MongoRepository<FlightSearchLog, String> {
}
