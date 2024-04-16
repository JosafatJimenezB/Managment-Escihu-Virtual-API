package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.persistence.Entity.Course.Course;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;
import com.escihu.apiescihuvirtual.service.Dashboard.DashboardService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "*")
public class Dashboard {

    private final DashboardService dashboardService;

    public Dashboard(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard/total_students")
    public ResponseEntity<?> totalStudents() {
        try {
            Integer total = dashboardService.getTotalStudents();
            return new ResponseEntity<>(total, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/dashboard/total_teachers")
    public ResponseEntity<?> totalTeachers() {
        try {
            Integer total = dashboardService.getTotalTeachers();
            return new ResponseEntity<>(total, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/dashboard/total_classrooms")
    public ResponseEntity<?> totalClassrooms() {
        try {
            Integer total = dashboardService.getTotalClassrooms();
            return new ResponseEntity<>(total, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/dashboard/courses/{teacher}/teacher")
    public ResponseEntity<?> getCoursesByTeacher(@PathVariable String teacher) {
        try {
            List<Course> courses = dashboardService.getCoursesByTeacher(teacher);

            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/dashboard/subjects/{teacher}/teacher")
    public ResponseEntity<?> getSubjectsByTeacher(@PathVariable String teacher) {
        try {
            List<Subject> subjects = dashboardService.getSubjectsByTeacher(teacher);

            return new ResponseEntity<>(subjects, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
