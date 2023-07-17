package dev.arkaan.schoolapp.roomservice;

import org.jdbi.v3.core.Jdbi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final Jdbi jdbi;

    public RoomController(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @GetMapping
    public ResponseEntity<String> getAllRooms() {
        jdbi.withHandle(handle -> handle.execute("SELECT 1"));
        return ResponseEntity.ok("ok");
    }
}
