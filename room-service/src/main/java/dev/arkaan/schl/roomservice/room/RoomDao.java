package dev.arkaan.schl.roomservice.room;

import dev.arkaan.schl.roomservice.exception.DuplicateException;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Repository
@Lazy
public class RoomDao {
    private final JdbcTemplate jdbcTemplate;

    public RoomDao(@Lazy JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Room> getAllRooms() {
        return jdbcTemplate.query("SELECT id, code, category_id FROM room", (rs, rowNum) -> {
            var id = rs.getShort(1);
            var code = rs.getString(2);
            var categoryId = rs.getShort(3);
            return new Room(id, code, categoryId);
        });
    }

    public void addRoom(String code, short categoryId) {
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement insertQuery = con.prepareStatement("INSERT INTO room (code, category_id) VALUES (?, ?)");
                insertQuery.setString(1, code);
                insertQuery.setShort(2, categoryId);
                return insertQuery;
            });
        } catch (DataAccessException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new DuplicateException();
            }
        }
    }

    public List<Category> getAllCategories() {
        return jdbcTemplate.query("SELECT id, name FROM category", (rs, rowNum) -> {
                    var id = rs.getShort(1);
                    var name = rs.getString(2);
                    return new Category(id, name);
        });
    }

    public void addCategory(String name) {
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement insertQuery = con.prepareStatement("INSERT INTO category (name) VALUES (?)");
                insertQuery.setString(1, name);
                return insertQuery;
            });
        } catch (DataAccessException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new DuplicateException();
            }
        }
    }
}
