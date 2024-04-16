package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.Dto.Student.StudentDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Student.StudentDtoResponse;
import com.escihu.apiescihuvirtual.Dto.Student.StudentUpdateDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import com.escihu.apiescihuvirtual.service.Student.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Controlador de estudiantes")
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students/paginated/{pageSize}")
    public ResponseEntity<?> getStudentsPaginated(Pageable pageable, @RequestParam (value = "pageSize", defaultValue = "10") int pageSize) {
        try {
            Pageable modifiedPage = PageRequest.of(pageable.getPageNumber(), pageSize);
            return new ResponseEntity<>(studentService.listStudentsPaginated(modifiedPage), HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/students/list")
    public ResponseEntity<?> listAll() {
        try{
            List<StudentDtoResponse> students = studentService.listStudents();
            return new ResponseEntity<>(students, HttpStatus.OK);
        }catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Student student = studentService.getStudentById(id);

        if(student == null) {
            return new ResponseEntity<>(Message.builder()
                    .message("Student not found")
                    .object(null)
                    .build(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/student")
    public ResponseEntity<?> create(@RequestBody StudentDtoRequest studentDtoRequest) {
        Student student;
        try{
             student = studentService.createStudent(studentDtoRequest);
            return new ResponseEntity<>(Message.builder()
                    .message("Student created succesfully")
                    .object(student)
                    .build(), HttpStatus.CREATED);
        } catch (DataAccessException e){
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id, @RequestBody StudentUpdateDtoRequest studentDtoRequest) {
        Student student = null;

        try {
            if(!studentService.exists(id)){
                return new ResponseEntity<>(Message.builder()
                        .message("Student not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }

            student = studentService.updateStudent(id, studentDtoRequest);

            return new ResponseEntity<>(Message.builder()
                    .message("Student updated succesfully")
                    .object(student)
                    .build(), HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

}
