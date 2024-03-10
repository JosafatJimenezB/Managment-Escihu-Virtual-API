package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.persistence.Entity.Classroom.Classroom;
import com.escihu.apiescihuvirtual.persistence.Entity.Grade.Grade;
import com.escihu.apiescihuvirtual.service.Grade.GradeService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/grades/{id}/subject")
    public ResponseEntity<?> listAll(@PathVariable Long id) {
        try{
            List<Grade> grades = gradeService.getGradesBySubjectId(id);

            return new ResponseEntity<>(grades, HttpStatus.OK);
        }catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }
}
