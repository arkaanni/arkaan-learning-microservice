package dev.arkaan.schl.roomservice.schedule;

import dev.arkaan.schl.roomservice.exception.DuplicateException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room/schedule")
@CrossOrigin(origins = "*")
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

    @PostMapping
    public ResponseEntity<String> addSchedule(
            @RequestBody Schedule schedule
    ) {
        try {
            scheduleDao.addSchedule(schedule);
            return ResponseEntity.ok().build();
        } catch (DuplicateException e) {
            return ResponseEntity.badRequest().body("Already exists?");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Schedule> getSchedule(
            @PathVariable String id
    ) {
        Schedule schedule = scheduleDao.getSchedule(id);
        if (schedule == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(schedule);
    }

    @PostMapping("/recurring")
    public ResponseEntity<String> addRecurringSchedule(
            @RequestBody RecurringSchedule schedule
    ) {
        scheduleDao.addRecurringSchedule(schedule);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recurring")
    public ResponseEntity<List<RecurringSchedule>> getRecurringSchedule() {
        var recurringScheduleList = scheduleDao.getRecurringSchedule();
        return ResponseEntity.ok(recurringScheduleList);
    }

    @GetMapping("/recurring/{id}")
    public ResponseEntity<RecurringSchedule> getRecurringSchedule(
            @PathVariable String id
    ) {
        RecurringSchedule schedule = scheduleDao.getRecurringSchedule(id);
        if (schedule == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(schedule);
    }
}
