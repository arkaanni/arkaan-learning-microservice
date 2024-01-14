package dev.arkaan.schoolapp.roomservice.room;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomDao roomDao;

    public RoomController(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRoom() {
        List<Room> roomList = roomDao.getAllRooms();
        return ResponseEntity.ok(roomList);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categoryList = roomDao.getAllCategories();
        return ResponseEntity.ok(categoryList);
    }
}
