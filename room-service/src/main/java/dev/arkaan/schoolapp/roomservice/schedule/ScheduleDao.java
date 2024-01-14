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

    public void addRecurringSchedule(RecurringSchedule schedule) {
        jdbi.useTransaction(handle -> {
            handle.createUpdate("""
                    INSERT INTO recurring_schedule (day, hour_start, hour_end, room_id, minute_start, minute_end, `from`, until)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    """)
                    .bind(0, schedule.day())
                    .bind(1, schedule.hourStart())
                    .bind(2, schedule.hourEnd())
                    .bind(3, schedule.roomId())
                    .bind(4, schedule.minuteStart())
                    .bind(5, schedule.minuteEnd())
                    .bind(6, schedule.from())
                    .bind(7, schedule.until())
                    .execute();
        });
    }
}
