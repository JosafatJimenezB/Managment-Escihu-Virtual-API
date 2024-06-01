package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.CourseDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.persistence.Entity.Course.Course;
import com.escihu.apiescihuvirtual.persistence.Entity.Cycle.Cycle;
import com.escihu.apiescihuvirtual.service.Course.CourseService;
import com.escihu.apiescihuvirtual.service.Student.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Controlador de cursos")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class CourseController {

    private final CourseService courseService;
    private final StudentService studentService;

    public CourseController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @GetMapping("/courses/cycle/{id}")
    public ResponseEntity<?> listAll(@PathVariable Long id) {

        try {
            List<Course> courses = courseService.getAllCoursesByCycleId(id);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @GetMapping("/courses/cycle")
    public ResponseEntity<?> listCourse(@RequestBody Cycle cycle) {
        try {
            List<Course> courses = courseService.getAllCoursesByCycle(cycle);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        try {
            Course course = courseService.getCourseById(id);

            if (course == null) {
                return new ResponseEntity<>(Message.builder()
                        .message("Course not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @PostMapping("/courses")
    public ResponseEntity<?> addCourse(@RequestBody CourseDtoRequest courseDtoRequest) {
        try {
            courseService.addCourse(courseDtoRequest);

            return new ResponseEntity<>(Message.builder()
                    .message("Course Added succesfully")
                    .object(null)
                    .build(), HttpStatus.CREATED);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody CourseDtoRequest courseDtoRequest) {
        Course course = null;

        try {

            if (courseService.existCourse(id)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Course not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }

            courseService.updateCourse(id, courseDtoRequest);

            return new ResponseEntity<>(Message.builder()
                    .message("Course Updated succesfully")
                    .object(null)
                    .build(), HttpStatus.CREATED);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @DeleteMapping("/courses/{idCourse}/unsuscribe/student/{idStudent}")
    public ResponseEntity<?> unsuscribeStudent(@PathVariable Long idCourse, @PathVariable Long idStudent) {
        try {
            if (courseService.existCourse(idCourse)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Course not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else if (studentService.existsStudent(idStudent)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Student not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }

            courseService.unsubscribeStudent(idCourse, idStudent);

            return new ResponseEntity<>(Message.builder()
                    .message("Student unsuscribed succesfully")
                    .object(null), HttpStatus.OK);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("/courses/{idCourse}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long idCourse) {
        try {
            if (courseService.existCourse(idCourse)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Course not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }
            courseService.deleteCourseById(idCourse);

            return new ResponseEntity<>(Message.builder()
                    .message("Course deleted succesfully")
                    .object(null), HttpStatus.OK);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
