package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;
import com.escihu.apiescihuvirtual.service.Course.CourseService;
import com.escihu.apiescihuvirtual.service.Subject.SubjectService;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class SubjectController {

    private final SubjectService subjectService;
    private final CourseService courseService;

    public SubjectController(SubjectService subjectService, CourseService courseService) {
        this.subjectService = subjectService;
        this.courseService = courseService;
    }

    @GetMapping("/subjects/{courseId}/course")
    public ResponseEntity<?> getAllSubjectsById(@PathVariable Long courseId) {
        try {
            if (!courseService.existById(courseId)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Course not found")
                        .object(null), HttpStatus.NOT_FOUND);
            }

            List<Subject> subjects = subjectService.getAllSubjectsByCourseId(courseId);

            return new ResponseEntity<>(subjects, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable Long id) {
        try {
            if (!subjectService.existById(id)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Subject not found")
                        .object(null), HttpStatus.NOT_FOUND);
            }

            Subject subject = subjectService.getSubjectById(id);

            return new ResponseEntity<>(subject, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping("/subjects")
    public ResponseEntity<?> create(@RequestBody Subject subjectRequest) {
        try {

            Subject subject = subjectService.addSubject(subjectRequest);

            return new ResponseEntity<>(subject, HttpStatus.CREATED);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("/schedules/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, Subject subjectRequest) {
        try {
            if(!subjectService.existById(id)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Subject not found")
                        .object(null), HttpStatus.NOT_FOUND);
            }

            Subject subject = subjectService.updateSubject(id, subjectRequest);

            return new ResponseEntity<>(Message.builder()
                    .message("Subject updated succesfully")
                    .object(subject), HttpStatus.OK);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<?> deleteSubjectById(@PathVariable Long id) {
        try {
            if (!subjectService.existById(id)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Subject not found")
                        .object(null), HttpStatus.NOT_FOUND);
            }

            subjectService.deleteSubjectById(id);

            return new ResponseEntity<>(Message.builder()
                    .message("Subject deleted Succesfully")
                    .object(null), HttpStatus.NO_CONTENT);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
