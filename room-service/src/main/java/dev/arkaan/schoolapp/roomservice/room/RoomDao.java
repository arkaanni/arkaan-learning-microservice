package dev.arkaan.schoolapp.roomservice.room;

import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomDao {
    private final Jdbi jdbi;

    public RoomDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<Room> getAllRooms() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT id, code, category_id FROM room")
                .map((rs, ctx) -> {
                    var id = rs.getShort(1);
                    var code = rs.getString(2);
                    var categoryId = rs.getShort(3);
                    return new Room(id, code, categoryId);
                }).list());
    }
}
