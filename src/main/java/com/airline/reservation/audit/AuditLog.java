package com.airline.reservation.audit;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    private String id;

    private String eventType;
    private String entityType;
    private String entityId;

    private Long userId;
    private String email;

    private String description;
    private boolean success;

    private Map<String, Object> metadata;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
