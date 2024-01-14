package dev.arkaan.schoolapp.roomservice.schedule;

import java.time.LocalDate;

public record RecurringSchedule(
        String id,
        short roomId,
        short day,
        short hourStart,
        short hourEnd,
        short minuteStart,
        short minuteEnd,
        LocalDate from,
        LocalDate until
) {
}
