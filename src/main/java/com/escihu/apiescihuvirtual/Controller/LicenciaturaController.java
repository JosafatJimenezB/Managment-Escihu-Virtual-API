package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Licenciatura.LicenciaturaDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.persistence.Entity.Licenciatura.Licenciatura;
import com.escihu.apiescihuvirtual.service.Licenciatura.LicenciaturaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "Controlador de Licenciaturas")
public class LicenciaturaController {

     private final LicenciaturaService service;

    public LicenciaturaController(LicenciaturaService service) {
        this.service = service;
    }

    @GetMapping("/licenciaturas")
    public ResponseEntity<?> listAll() {
        try {
            List<Licenciatura> licenciaturas = service.listAll();

            return new ResponseEntity<>(licenciaturas, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/licenciaturas/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Licenciatura licenciatura = service.getById(id);

            return new ResponseEntity<>(licenciatura, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping("/licenciatura")
    public ResponseEntity<?> create(@RequestBody LicenciaturaDtoRequest licenciaturaDtoRequest) {
        Licenciatura licenciatura;
        try {

            licenciatura = service.create(licenciaturaDtoRequest);

            return new ResponseEntity<>(Message.builder()
                    .message("Licenciatura created succesfully")
                    .object(licenciatura)
                    .build(), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("/licenciatura/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody LicenciaturaDtoRequest licenciaturaDtoRequest) {
        try {
            if (!service.existById(id)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Licenciatura not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }

            Licenciatura licenciatura = service. update(id, licenciaturaDtoRequest);

            return new ResponseEntity<>(Message.builder()
                    .message("Licenciatura updated succesfully")
                    .object(licenciatura), HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("/licenciatura/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            if (!service.existById(id)) {
                return new ResponseEntity<>(Message.builder()
                        .message("Licenciatura not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }

            service.delete(id);

            return new ResponseEntity<>(Message.builder()
                    .message("Licenciatura deleted succesfully")
                    .object(null)
                    .build(), HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMostSpecificCause().getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
