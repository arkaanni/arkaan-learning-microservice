package dev.arkaan.schoolapp.roomservice.schedule;

import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ScheduleDao {
    private final Jdbi jdbi;

    public ScheduleDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<Schedule> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT id, description, start, until, room_id FROM schedule")
                .map((rs, ctx) -> {
                    String id = rs.getString(1);
                    String desc = rs.getString(2);
                    LocalDateTime start = rs.getTimestamp(3).toLocalDateTime();
                    LocalDateTime until = rs.getTimestamp(4).toLocalDateTime();
                    short roomId = rs.getShort(5);
                    return new Schedule(id, desc, start, until, roomId);
                })
                .list()
        );
    }
}
