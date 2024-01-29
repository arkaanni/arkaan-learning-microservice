package dev.arkaan.schl.roomservice.room;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Room(
        short id,
        String code,
        short categoryId) {
}
