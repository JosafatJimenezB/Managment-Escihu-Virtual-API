package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.persistence.Entity.Grade.GradeDetail;
import com.escihu.apiescihuvirtual.service.GradeDetails.GradeDetailsService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "*")
public class GradeDetailController {

    private final GradeDetailsService gradeDetailsService;

    public GradeDetailController(GradeDetailsService gradeDetailsService) {
        this.gradeDetailsService = gradeDetailsService;
    }

    @GetMapping("/grade_details/{id}")
    public ResponseEntity<?> getScoreById(@PathVariable Long id) {
        try {

            if (!gradeDetailsService.existById(id)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Grade Details not found")
                        .object(null), HttpStatus.NOT_FOUND);
            }

            GradeDetail detail = gradeDetailsService.getScoreById(id);
            return new ResponseEntity<>(detail, HttpStatus.OK);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("/grade_details")
    public ResponseEntity<?> updateGradeDetail(@RequestBody GradeDetail gradeDetail) {
        try {

            if (!gradeDetailsService.existById(gradeDetail.getId())) {
                return new ResponseEntity<>(Message.builder()
                        .message("Grade Details not found")
                        .object(null), HttpStatus.NOT_FOUND);
            }

            GradeDetail grade = gradeDetailsService.updateScore(gradeDetail);

            return new ResponseEntity<>(Message.builder()
                    .message("Grade details updated succesfully")
                    .object(grade), HttpStatus.OK);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
