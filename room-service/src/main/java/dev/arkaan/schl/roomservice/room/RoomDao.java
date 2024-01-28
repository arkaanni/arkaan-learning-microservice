package dev.arkaan.schl.roomservice.room;

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

    public void addRoom(String code, short categoryId) {
        jdbi.useTransaction(handle -> handle.createUpdate("INSERT INTO room (code, category_id) VALUES (?, ?)")
                .bind(0, code)
                .bind(1, categoryId)
                .execute());
    }

    public List<Category> getAllCategories() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT id, name FROM category")
                .map((rs, ctx) -> {
                    var id = rs.getShort(1);
                    var name = rs.getString(2);
                    return new Category(id, name);
                }).list());
    }

    public void addCategory(String name) {
        jdbi.useTransaction(handle -> handle.createUpdate("INSERT INTO category (name) VALUES (?)")
                .bind(0, name)
                .execute());
    }
}
