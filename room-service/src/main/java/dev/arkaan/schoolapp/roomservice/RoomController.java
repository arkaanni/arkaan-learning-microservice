package dev.arkaan.schoolapp.roomservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class RoomController {

    @GetMapping
    public ResponseEntity<String> getAllRooms() {
        return ResponseEntity.ok("ok");
    }
}
