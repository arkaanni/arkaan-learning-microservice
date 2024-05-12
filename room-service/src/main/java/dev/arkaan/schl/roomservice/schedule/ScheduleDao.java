package dev.arkaan.schl.roomservice.schedule;

import dev.arkaan.schl.roomservice.exception.DuplicateException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ScheduleDao {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Schedule> getAll() {
        return jdbcTemplate.query("SELECT id, description, start, until, room_id FROM schedule", (rs, rowNum) -> {
            String id = rs.getString(1);
            String desc = rs.getString(2);
            LocalDate start = rs.getTimestamp(3).toLocalDateTime().toLocalDate();
            LocalDate until = rs.getTimestamp(4).toLocalDateTime().toLocalDate();
            short roomId = rs.getShort(5);
            return new Schedule(id, desc, start, until, roomId);
        });
    }

    public void addRecurringSchedule(RecurringSchedule schedule) {
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement insertQuery = con.prepareStatement(
                        """
                                INSERT INTO recurring_schedule (day, hour_start, hour_end, room_id, minute_start, minute_end, `from`, until)
                                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                                """);
                insertQuery.setShort(1, schedule.day());
                insertQuery.setShort(2, schedule.hourStart());
                insertQuery.setShort(3, schedule.hourEnd());
                insertQuery.setShort(4, schedule.roomId());
                insertQuery.setShort(5, schedule.minuteStart());
                insertQuery.setShort(6, schedule.minuteEnd());
                insertQuery.setDate(7, Date.valueOf(schedule.from()));
                insertQuery.setDate(8, Date.valueOf(schedule.until()));
                return insertQuery;
            });
        } catch (DataAccessException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new DuplicateException();
            }
        }
    }

    public List<RecurringSchedule> getRecurringSchedule() {
        return jdbcTemplate.query("""
                SELECT id, `day`, hour_start, hour_end, room_id, minute_start, minute_end, `from`, until
                FROM room.recurring_schedule
                """, (rs, rowNum) -> {
            String id = rs.getString(1);
            short day = rs.getShort(2);
            short hourStart = rs.getShort(3);
            short hourEnd = rs.getShort(4);
            short roomId = rs.getShort(5);
            short minuteStart = rs.getShort(6);
            short minuteEnd = rs.getShort(7);
            LocalDate from = rs.getDate(8).toLocalDate();
            LocalDate until = rs.getDate(9).toLocalDate();
            return new RecurringSchedule(id, roomId, day, hourStart, hourEnd, minuteStart, minuteEnd, from, until);
        });
    }

    public Schedule getSchedule(String id) {
        return jdbcTemplate.query((con) -> {
            PreparedStatement query = con.prepareStatement("SELECT description, start, until, room_id FROM schedule WHERE id=?");
            query.setString(1, id);
            return  query;
        }, (rs) -> {
            if (!rs.next()) {
                return null;
            }
            String desc = rs.getString(1);
            LocalDate start = rs.getTimestamp(2).toLocalDateTime().toLocalDate();
            LocalDate until = rs.getTimestamp(3).toLocalDateTime().toLocalDate();
            short roomId = rs.getShort(4);
            return new Schedule(id, desc, start, until, roomId);
        });
    }

    public RecurringSchedule getRecurringSchedule(String id) {
        return jdbcTemplate.query((con) -> {
            PreparedStatement query = con.prepareStatement("""
                SELECT `day`, hour_start, hour_end, room_id, minute_start, minute_end, `from`, until
                FROM room.recurring_schedule
                WHERE id=?
                """);
            query.setString(0, id);
            return query;
        }, (rs) -> {
            if (!rs.next()) {
                return null;
            }
            short day = rs.getShort(1);
            short hourStart = rs.getShort(2);
            short hourEnd = rs.getShort(3);
            short roomId = rs.getShort(4);
            short minuteStart = rs.getShort(5);
            short minuteEnd = rs.getShort(6);
            LocalDate from = rs.getDate(7).toLocalDate();
            LocalDate until = rs.getDate(8).toLocalDate();
            return new RecurringSchedule(id, roomId, day, hourStart, hourEnd, minuteStart, minuteEnd, from, until);
        });
    }

    public void addSchedule(Schedule schedule) {
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement insertQuery = con.prepareStatement("""
                      INSERT INTO room.schedule
                      (description, `start`, until, room_id)
                      VALUES(?, ?, ?, ?);
                      """);
                insertQuery.setString(1, schedule.description());
                insertQuery.setDate(2,  Date.valueOf(schedule.start()));
                insertQuery.setDate(3, Date.valueOf(schedule.until()));
                insertQuery.setShort(4, schedule.roomId());
                return insertQuery;
            });
        } catch (DataAccessException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new DuplicateException();
            }
        }
    }
}
