package com.airline.reservation.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final AuditLogRepository auditLogRepository;
    private final FlightSearchLogRepository flightSearchLogRepository;

    public void log(String eventType, String entityType, String entityId,
                    Long userId, String email, String description,
                    boolean success, Map<String, Object> metadata) {
        try {
            AuditLog entry = AuditLog.builder()
                    .eventType(eventType)
                    .entityType(entityType)
                    .entityId(entityId)
                    .userId(userId)
                    .email(email)
                    .description(description)
                    .success(success)
                    .metadata(metadata)
                    .build();
            auditLogRepository.save(entry);
        } catch (Exception e) {
            log.warn("Audit log write failed (non-critical): {}", e.getMessage());
        }
    }

    public void logFlightSearch(Long userId, String origin, String destination,
                                LocalDate travelDate, int resultsCount) {
        try {
            FlightSearchLog entry = FlightSearchLog.builder()
                    .userId(userId)
                    .origin(origin)
                    .destination(destination)
                    .travelDate(travelDate)
                    .resultsCount(resultsCount)
                    .build();
            flightSearchLogRepository.save(entry);
        } catch (Exception e) {
            log.warn("Flight search log write failed (non-critical): {}", e.getMessage());
        }
    }
}
