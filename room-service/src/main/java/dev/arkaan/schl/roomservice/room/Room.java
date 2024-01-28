package dev.arkaan.schl.roomservice.room;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Room(
        short id,
        String code,
        @JsonProperty("category_id")
        short categoryId) {
}
