package dev.arkaan.schl.roomservice.room;

import org.jdbi.v3.core.JdbiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomDao roomDao;

    public RoomController(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> roomList = roomDao.getAllRooms();
        return ResponseEntity.ok(roomList);
    }

    @PostMapping
    public ResponseEntity<String> addRoom(@RequestBody Room room) {
        try {
            roomDao.addRoom(room.code(), room.categoryId());
            return ResponseEntity.ok().build();
        } catch (JdbiException e) {
            return ResponseEntity.badRequest().body("Room with code " + room.code() + " already exists");
        }
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categoryList = roomDao.getAllCategories();
        return ResponseEntity.ok(categoryList);
    }

    @PostMapping("/category")
    public ResponseEntity<String> addRoomCategory(
            @RequestBody String name
    ) {
        try {
            roomDao.addCategory(name);
            return ResponseEntity.ok().build();
        } catch (JdbiException e) {
            return ResponseEntity.badRequest().body("Category already exists");
        }
    }
}
