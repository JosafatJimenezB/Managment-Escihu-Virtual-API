package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.Dto.Student.StudentDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Student.StudentDtoResponse;
import com.escihu.apiescihuvirtual.Dto.Student.StudentUpdateDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import com.escihu.apiescihuvirtual.service.Student.StudentService;
import com.escihu.apiescihuvirtual.service.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Tag(name = "Controlador de estudiantes")
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;
    private final UserServiceImpl userServiceImpl;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(StudentController.class);

    public StudentController(StudentService studentService, UserServiceImpl userServiceImpl) {
        this.studentService = studentService;
        this.userServiceImpl = userServiceImpl;
    }
    @Operation(summary = "Retorna una lista de estudiantes paginada",
            description = "Retorna una lista de estudiantes paginada, se puede especificar el número de página y el tamaño de la página.",
            tags = {"Controlador de estudiantes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiantes obtenidos correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDtoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error al obtener los estudiantes",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Message.class))),
    })
    @GetMapping("/students/paginated")
    public ResponseEntity<?> getStudentsPaginated(
            @RequestParam(defaultValue = "0") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            Pageable modifiedPage = PageRequest.of(currentPage, pageSize);
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
        try {
            List<StudentDtoResponse> students = studentService.listStudents();
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        logger.info("Student id {}", id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        logger.info("Current username {}", currentUsername);


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isStudent = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUDENT"));
        boolean isAdmin = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        Optional<Student> optionalStudent  = studentService.findById(id);

        if (!optionalStudent .isPresent()) {
            return new ResponseEntity<>(Message.builder()
                    .message("Student not found")
                    .object(null)
                    .build(), HttpStatus.NOT_FOUND);
        }

        Student student = optionalStudent.get();
        String studentUsername = student.getNombre().toLowerCase() + "." + student.getApellidoPaterno().toLowerCase();
        logger.info("Student username {}", studentUsername);

        // Verificar si el usuario es un estudiante y si el estudiante no pertenece al usuario actual
        if (isStudent && !studentUsername.equals(currentUsername)) {
            return new ResponseEntity<>(Message.builder()
                    .message("Access denied")
                    .object(null)
                    .build(), HttpStatus.FORBIDDEN);
        }




        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/student")
    public ResponseEntity<?> create(@RequestBody StudentDtoRequest studentDtoRequest) {
        Student student;
        try {
            student = studentService.createStudent(studentDtoRequest);
            return new ResponseEntity<>(Message.builder()
                    .message("Student created succesfully")
                    .object(student)
                    .build(), HttpStatus.CREATED);
        } catch (DataAccessException e) {
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
            if (studentService.existsStudent(id)) {
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
