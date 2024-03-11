package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.ClassroomDto;
import com.escihu.apiescihuvirtual.Dto.Cycle.CycleDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.persistence.Entity.Classroom.Classroom;
import com.escihu.apiescihuvirtual.persistence.Entity.Cycle.Cycle;
import com.escihu.apiescihuvirtual.service.Cycle.CycleService;
import com.escihu.apiescihuvirtual.service.Cycle.CycleServiceImpl;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CycleCourse {

    private final CycleService cycleService;

    public CycleCourse(CycleService cycleService) {
        this.cycleService = cycleService;
    }

    @GetMapping("/cycles/paginated")
    public ResponseEntity<?> pageableAll(@RequestParam(name = "page", defaultValue = "0") int page) {

        try{
            PageRequest pageable = PageRequest.of(page, 10);
            Page<Cycle> cycle = cycleService.getAllCycles(pageable);
            return new ResponseEntity<>(cycle, HttpStatus.OK);
        }catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @GetMapping("/cycles")
    public ResponseEntity<?> listAll() {
        try{
            List<Cycle> cycle = cycleService.getActiveCycles();
            return new ResponseEntity<>(cycle, HttpStatus.OK);
        }catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @GetMapping("/cycles/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){

        try {
            Cycle cycle = cycleService.getcycleById(id);

            if(cycle == null) {
                return new ResponseEntity<>(Message.builder()
                        .message("Clycle not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(cycle, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }

    }

    @PostMapping("/cycles")
    public ResponseEntity<?> create(@RequestBody CycleDtoRequest cycleDtoRequest) {
        try {
            Cycle cycle = cycleService.addCycle(cycleDtoRequest);
            return new ResponseEntity<>(Message.builder()
                    .message("Cycle created succesfully")
                    .object(cycle)
                    .build(), HttpStatus.CREATED);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @PutMapping("/cycles/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id , @RequestBody CycleDtoRequest cycleDtoRequest) {
        Cycle cycle = null;

        try{
            if(!cycleService.exists(id)){
                return new ResponseEntity<>(Message.builder()
                        .message("Cycle not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }

            cycle = cycleService.updateCycle(cycleDtoRequest, id);

            return new ResponseEntity<>(Message.builder()
                    .message("Cycle updated succesfully")
                    .object(cycle.builder()
                            .name(cycle.getName())
                            .createdAt(cycle.getCreatedAt())
                            .updatedAt(cycle.getUpdatedAt())
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
