package dev.arkaan.schoolapp.roomservice.schedule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
