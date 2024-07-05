package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.ReportInscriptionDTO;
import com.escihu.apiescihuvirtual.Dto.StudentReportDTO;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.StatusStudent;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import com.escihu.apiescihuvirtual.service.Student.StudentService;
import com.escihu.apiescihuvirtual.service.report.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/report-inscriptions")
    public ResponseEntity<String> reportInscriptions(@RequestBody ReportInscriptionDTO reportInscriptionDTO) throws JRException, FileNotFoundException {

        reportService.generateEnrollmentReport(reportInscriptionDTO);

        return ResponseEntity.ok("Report generated successfully");
    }
}
