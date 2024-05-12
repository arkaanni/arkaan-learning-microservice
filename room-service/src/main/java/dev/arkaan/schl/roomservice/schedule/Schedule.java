package dev.arkaan.schl.roomservice.schedule;

import java.time.LocalDate;

public record Schedule(
        String id,
        String description,
        LocalDate start,
        LocalDate until,
        short roomId
) {
}
