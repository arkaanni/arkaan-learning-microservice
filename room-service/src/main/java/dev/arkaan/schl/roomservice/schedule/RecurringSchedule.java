package dev.arkaan.schl.roomservice.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record RecurringSchedule(
        String id,
        @JsonProperty("room_id")
        short roomId,
        short day,
        @JsonProperty("hour_start")
        short hourStart,
        @JsonProperty("hour_end")
        short hourEnd,
        @JsonProperty("minute_start")
        short minuteStart,
        @JsonProperty("minute_end")
        short minuteEnd,
        LocalDate from,
        LocalDate until
) {
}
