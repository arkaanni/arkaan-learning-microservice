package dev.arkaan.schoolapp.roomservice.schedule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleDao scheduleDao;

    public ScheduleController(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAll() {
        List<Schedule> scheduleList = scheduleDao.getAll();
        return ResponseEntity.ok(scheduleList);
    }

    @PostMapping("/recurring")
    public ResponseEntity<String> addRecurringSchedule(
            @RequestBody RecurringSchedule schedule
    ) {
        scheduleDao.addRecurringSchedule(schedule);
        return ResponseEntity.ok().build();
    }
}
