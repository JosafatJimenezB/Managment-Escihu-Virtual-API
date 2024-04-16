package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.ClassroomDto;
import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.persistence.Entity.Classroom.Classroom;
import com.escihu.apiescihuvirtual.service.Classroom.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Controlador de salones")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1")
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @Operation(summary = "Retorna todos los salones con paginación")
    @GetMapping("/classrooms")
    public ResponseEntity<?> pageableAll(@RequestParam(name = "page", defaultValue = "0") int page) {

        try{
            PageRequest pageable = PageRequest.of(page, 10);
            Page<Classroom> classrooms = classroomService.getAllClassrooms(pageable);
            return new ResponseEntity<>(classrooms, HttpStatus.OK);
        }catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @Operation(summary = "Retornar todos los salones en una lista")
    @GetMapping("/classrooms/list")
    public ResponseEntity<?> listAll() {
        try{
            List<Classroom> classrooms = classroomService.getAllClassrooms();

            return new ResponseEntity<>(classrooms, HttpStatus.OK);
        }catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @Operation(summary = "Retornar un salon por id")
    @GetMapping("/classroom/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){

        try {
            Classroom classroom = classroomService.getClassroomById(id);

            if(classroom == null) {
                return new ResponseEntity<>(Message.builder()
                        .message("Classroom not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(classroom, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @Operation(summary = "Crea um nuevo salon")
    @PostMapping("/classroom")
    public ResponseEntity<?> create(@Valid @RequestBody ClassroomDto classroomDto) {
        Classroom classroom = null;

        try{
            classroom = classroomService.addClassroom(classroomDto);

            return new ResponseEntity<>(Message.builder()
                    .message("Classroom created succesfully")
                    .object(classroom)
                    .build(), HttpStatus.CREATED);

        }catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @Operation(summary = "Actualiza un salon por id")
    @PutMapping("/classroom/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id , @RequestBody ClassroomDto classroomDto) {
        Classroom classroom = null;

        try{

            if(!classroomService.exists(id)){
                return new ResponseEntity<>(Message.builder()
                        .message("Classroom not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }

            classroom = classroomService.updateClassroom(id, classroomDto);

            return new ResponseEntity<>(Message.builder()
                    .message("Classroom updated succesfully")
                    .object(classroom.builder()
                            .id(classroom.getId())
                            .name(classroom.getName())
                            .description(classroom.getDescription())
                            .createdAt(classroom.getCreatedAt())
                            .updatedAt(classroom.getUpdatedAt())
                            .build())
                    .build(), HttpStatus.OK);

        }catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
