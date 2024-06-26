package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Attendance.AttendaceDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.persistence.Entity.Attendance.Attendance;
import com.escihu.apiescihuvirtual.service.Attendance.AttendanceServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "Controlador de asistencias")
public class AttendanceController {

    private final AttendanceServiceImpl attendanceService;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(AttendanceController.class);

    public AttendanceController(AttendanceServiceImpl attendanceService) {
        this.attendanceService = attendanceService;
    }

    @Operation(summary = "Retorna un listado de todas las asistencias registradas")
    @GetMapping("/attendance")
    public ResponseEntity<?> listAll() {
        try {
            List<Attendance> attendaceList = attendanceService.listAll();

            return new ResponseEntity<>(attendaceList, HttpStatus.OK);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Registro de asistencia por medio de un usuario")
    @PostMapping("/attendance/register")
    public ResponseEntity<?> registerAttendance(@Valid @RequestBody AttendaceDtoRequest attendanceDto) {
        logger.info("Attendance created {}", attendanceDto.getTypeAttendace());
        Attendance attendance;

        try {

            attendance = attendanceService.register(attendanceDto);

            if (attendance == null) {
                return new ResponseEntity<>(Message.builder()
                        .message("User not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(Message.builder()
                    .message("Attendace succesfully registered")
                    .object(attendance)
                    .build(), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/attendance/paginated")
    public ResponseEntity<?> paginatedAttendance(
            @RequestParam(defaultValue = "0") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize) {

        try {
            Pageable modifiedPage = PageRequest.of(currentPage, pageSize);
            Page<Attendance> attendancePage = attendanceService.attendancePagination(modifiedPage);
            return new ResponseEntity<>(attendancePage, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/attendance/{userId}/paginated")
    public ResponseEntity<?> paginatedAttendanceByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize) {

        try {
            Pageable modifiedPage = PageRequest.of(currentPage, pageSize);
            Page<Attendance> attendancePage = attendanceService.attendaceByUserId(userId, modifiedPage);
            logger.info("Attendance by user {} - {}", userId, attendancePage);
            return new ResponseEntity<>(attendancePage, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }

    }

}
