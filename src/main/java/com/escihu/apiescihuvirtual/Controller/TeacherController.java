package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.Dto.Teacher.TeacherDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Teacher.TeacherDtoResponse;
import com.escihu.apiescihuvirtual.persistence.Entity.Teacher.Teacher;
import com.escihu.apiescihuvirtual.service.Teacher.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Controlador de docentes")
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Operation(summary = "Retorna todos los docentes con paginación",
            description = "Este endpoint permite obtener una lista paginada de docentes. Por defecto, el tamaño de la página es de 10 elementos. Puedes especificar el número de página a través del parámetro de consulta `page` (por defecto la página 0).",
            parameters = {
                    @Parameter(description = "Número de la página de resultados (comenzando en 0)", name = "page", required = false, in = ParameterIn.QUERY)
            })
    @GetMapping("/teachers")
    public ResponseEntity<?> pageableAll(@RequestParam(name = "page", defaultValue = "0") int page) {

        try{
            PageRequest pageable = PageRequest.of(page, 10);
            Page<Teacher> teachers = teacherService.getAllTeachers(pageable);
            return new ResponseEntity<>(teachers, HttpStatus.OK);
        }catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @Operation(summary = "Retorna todos los docentes con paginación sorteados",
            description = "Opcionalmente, se puede incluir en la petición el tamaño de la página con el parámetro `pageSize`. Por defecto el tamaño de la página es 10",
            parameters = {
            @Parameter(description = "Tamaño de la página", name = "pageSize", required = false, in = ParameterIn.QUERY)
    })
    @GetMapping("/teachers/paginated/{pageSize}")
    public ResponseEntity<?> getTeachersPaginated(Pageable pageable, @RequestParam (value = "pageSize", defaultValue = "10") int pageSize) {
        try{
            Pageable modifiedPage = PageRequest.of(pageable.getPageNumber(), pageSize);
            return new ResponseEntity<>(teacherService.listTeachersPaginated(modifiedPage), HttpStatus.OK);
        }catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @Operation(summary = "Retorna todos los docentes para catálogo",
            description = "Retorna una lista de docentes con la información relevante (ID, nombre completo) para ser utilizada en un componente de selección (select) en el frontend.")
    @GetMapping("/teacher/list")
    public ResponseEntity<?> listAll() {
        try{
            List<TeacherDtoResponse> teachers = teacherService.listTeachers();
            return new ResponseEntity<>(teachers, HttpStatus.OK);
        }catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @Operation(summary = "Retorna un docente por ID",
            description = "Este endpoint permite obtener la información detallada de un docente específico a partir de su ID.",
            parameters = {
                    @Parameter(description = "ID del docente a buscar", name= "id", required = true, in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Docente encontrado", content = @Content(schema = @Schema(implementation = Teacher.class))),
                    @ApiResponse(responseCode = "404", description = "Docente no encontrado", content = @Content(schema = @Schema(implementation = Message.class)))
            })
    @GetMapping("/teacher/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Teacher teacher = teacherService.getTeacherById(id);

        if(teacher == null) {
            return new ResponseEntity<>(Message.builder()
                    .message("Teacher not found")
                    .object(null)
                    .build(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }

    @Operation(summary = "Crea un nuevo docente",
            description = "Este endpoint permite crear un nuevo registro de docente. Se debe proporcionar un objeto JSON con los datos del docente en el cuerpo de la solicitud.",
            requestBody = @RequestBody(content = @Content(mediaType = "application/json", schema = @Schema(implementation = TeacherDtoRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Docente creado exitosamente", content = @Content(schema = @Schema(implementation = Message.class))),
                    @ApiResponse(responseCode = "400", description = "Error en los datos proporcionados", content = @Content(schema = @Schema(implementation = Message.class)))
            })
    @PostMapping("/teacher")
    public ResponseEntity<?> create(@Valid @RequestBody TeacherDtoRequest teacherDtoRequest) {
        Teacher teacher = null;

        try{
            teacher = teacherService.createTeacher(teacherDtoRequest);

            return new ResponseEntity<>(Message.builder()
                    .message("Teacher created succesfully")
                    .object(teacher)
                    .build(), HttpStatus.CREATED);
        } catch (DataAccessException e){
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Actualiza un docente por ID",
            description = "Este endpoint permite actualizar un registro de docente existente. Se debe proporcionar el ID del docente como parámetro, y el objeto JSON actualizado en el cuerpo de la solicitud.",
            parameters = {
                    @Parameter(name = "id", description = "El ID del docente a actualizar", required = true, example = "1")
            },
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TeacherDtoRequest.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                            "nombre": "Juan Carlos",   // Nombre actualizado
                            "apellido": "López Gómez",  // Apellido actualizado
                            "correo": "juan.lopez@correo.com",
                            "telefono": "5512345678"
                        }
                        """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Docente actualizado exitosamente",
                            content = @Content(schema = @Schema(implementation = Teacher.class))),
                    @ApiResponse(responseCode = "400", description = "Error en los datos proporcionados",
                            content = @Content(schema = @Schema(implementation = Message.class))),
                    @ApiResponse(responseCode = "404", description = "Docente no encontrado",
                            content = @Content(schema = @Schema(implementation = Message.class)))
            }
    )
    @PutMapping("/teacher/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id, @RequestBody TeacherDtoRequest teacherDtoRequest) {
        Teacher teacher = null;

        try {
            if(!teacherService.exists(id)){
                return new ResponseEntity<>(Message.builder()
                        .message("Teacher not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }

            teacher = teacherService.updateTeacher(id, teacherDtoRequest);

            return new ResponseEntity<>(Message.builder()
                    .message("Teacher updated succesfully")
                    .object(teacher)
                    .build(), HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }
}