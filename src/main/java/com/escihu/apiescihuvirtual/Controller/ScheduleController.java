package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.SubjectSchedule;
import com.escihu.apiescihuvirtual.service.Course.CourseService;
import com.escihu.apiescihuvirtual.service.Schedule.ScheduleService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final CourseService courseService;

    public ScheduleController(ScheduleService scheduleService, CourseService courseService) {
        this.scheduleService = scheduleService;
        this.courseService = courseService;
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            if(!scheduleService.existById(id)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Schedule not found")
                        .object(null), HttpStatus.NOT_FOUND);
            }

            Optional<SubjectSchedule> subjectSchedule = scheduleService.getScheduleById(id);

            return new ResponseEntity<>(subjectSchedule, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/schedules/{id}/course")
    public ResponseEntity<?> getScheduleByCourseId(@PathVariable Long id) {
        try {
            if (!courseService.existById(id)){
                return new ResponseEntity<>(Message.builder()
                        .message("Course Not found")
                        .object(null), HttpStatus.NOT_FOUND);
            }

            HashMap<String, List<SubjectSchedule>> schedules = scheduleService.getScheduleByCourseId(id);

            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping("/schedules")
    public ResponseEntity<?> create(@RequestBody SubjectSchedule subjectSchedule) {
        try {
            SubjectSchedule schedule = scheduleService.save(subjectSchedule);

            return new ResponseEntity<>(Message.builder()
                    .message("Schedule created succesfully")
                    .object(schedule), HttpStatus.CREATED);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            if(!scheduleService.existById(id)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Schedule not found")
                        .object(null), HttpStatus.NOT_FOUND);
            }

            scheduleService.deleteSchedule(id);

            return new ResponseEntity<>(Message.builder()
                    .message("Schedule deleted succesfully")
                    .object(null),HttpStatus.NO_CONTENT);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

}
