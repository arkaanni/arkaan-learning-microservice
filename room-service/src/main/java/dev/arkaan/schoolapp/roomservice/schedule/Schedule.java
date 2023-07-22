package dev.arkaan.schoolapp.roomservice.schedule;

import java.time.LocalDateTime;

public record Schedule(
        String id,
        String description,
        LocalDateTime start,
        LocalDateTime until,
        short roomId
) {
}
