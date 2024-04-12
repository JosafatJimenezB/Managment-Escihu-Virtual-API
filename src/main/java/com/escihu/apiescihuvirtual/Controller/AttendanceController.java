package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Attendance.AttendaceDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.persistence.Entity.Attendance.Attendance;
import com.escihu.apiescihuvirtual.service.Attendance.AttendanceServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@Tag(name = "Controlador de asistencias")
public class AttendanceController {

    private final AttendanceServiceImpl attendanceService;

    public AttendanceController(AttendanceServiceImpl attendanceService) {
        this.attendanceService = attendanceService;
    }

    @Operation(summary = "Retorna un listado de todas las asistencias registradas")
    @GetMapping("/attendance")
    public ResponseEntity<?> listAll(){
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
    public ResponseEntity<?> registerAttendance(@RequestBody AttendaceDtoRequest attendanceDto) {
        Attendance attendance = null;

        try{

            attendance = attendanceService.register(attendanceDto);

            if(attendance == null) {
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

}
