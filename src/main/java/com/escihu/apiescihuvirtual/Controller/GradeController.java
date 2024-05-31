package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Grade.GradeDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.persistence.Entity.Grade.Grade;
import com.escihu.apiescihuvirtual.persistence.Entity.Grade.GradeDetail;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;
import com.escihu.apiescihuvirtual.service.Grade.GradeService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "*")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/grades/{id}/subject")
    public ResponseEntity<?> listAll(@PathVariable Long id) {
        try {
            List<Grade> grades = gradeService.getGradesBySubjectId(id);

            return new ResponseEntity<>(grades, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @GetMapping("/grades/{id}/scorebook")
    public ResponseEntity<?> getGradeScorebook(@PathVariable Long id) {
        try {

            if (!gradeService.existById(id)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Grade not found")
                        .object(null), HttpStatus.NOT_FOUND);
            }

            List<GradeDetail> gradeDetails = gradeService.getGradeScorebook(id);

            return new ResponseEntity<>(gradeDetails, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @PostMapping("/grades/getBySubject")
    public ResponseEntity<?> getGradesBySubject(@RequestBody Subject subject) {
        try {
            List<Grade> grades = gradeService.getGradesBySubject(subject);

            return new ResponseEntity<>(grades, HttpStatus.OK);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("/grades/{id}")
    public ResponseEntity<?> updateGrade(@PathVariable Long id, @RequestBody GradeDtoRequest gradeDtoRequest) {
        try {
            Grade grade = gradeService.updateGrade(id, gradeDtoRequest);

            if (!gradeService.existById(id)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Grade not found")
                        .object(null), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(Message.builder()
                    .message("Grade updated succesfully")
                    .object(grade), HttpStatus.OK);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping("/grades")
    public ResponseEntity<?> addSubject(@RequestBody GradeDtoRequest gradeDtoRequest) {
        try {
            Grade grade = gradeService.addGrade(gradeDtoRequest);

            return new ResponseEntity<>(Message.builder()
                    .message("Subject created succesfully")
                    .object(grade), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/grades/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Grade grade = gradeService.getGradeById(id);

            if (!gradeService.existById(id)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Grade not found")
                        .object(null), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(grade, HttpStatus.OK);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("/grades/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {

            if (!gradeService.existById(id)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Grade not found")
                        .object(null), HttpStatus.NOT_FOUND);
            }

            gradeService.deleteGradeById(id);

            return new ResponseEntity<>(Message.builder()
                    .message("Grade eliminated succesfully")
                    .object(null), HttpStatus.NO_CONTENT);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }


    @DeleteMapping("/grades")
    public ResponseEntity<?> delete(@RequestBody Grade grade) {
        try {

            if (!gradeService.existById(grade.getId())) {
                return new ResponseEntity<>(Message.builder()
                        .message("Grade not found")
                        .object(null), HttpStatus.NOT_FOUND);
            }

            gradeService.deleteGrade(grade);

            return new ResponseEntity<>(Message.builder()
                    .message("Grade eliminated succesfully")
                    .object(null), HttpStatus.NO_CONTENT);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }


}
