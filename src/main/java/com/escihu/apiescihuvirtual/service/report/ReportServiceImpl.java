package com.escihu.apiescihuvirtual.service.report;

import com.escihu.apiescihuvirtual.Dto.ReportInscriptionDTO;
import com.escihu.apiescihuvirtual.Dto.StudentReportDTO;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.StatusStudent;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import com.escihu.apiescihuvirtual.service.Student.StudentService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    private final StudentService studentService;

    public ReportServiceImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void generateEnrollmentReport(ReportInscriptionDTO reportInscriptionDTO) throws FileNotFoundException, JRException {
        List<Student> students = studentService.findStudentsByStatusAlumno(StatusStudent.PROCESO_INSCRIPCION);

        LocalDate now = LocalDate.now();

        String day = String.valueOf(now.getDayOfMonth());
        String month = String.valueOf(now.getMonth().getValue());
        String year = String.valueOf(now.getYear());

        System.out.println("Day: " + day);
        System.out.println("Month: " + month);
        System.out.println("Year: " + year);


        List<StudentReportDTO> studentReportDTOList = students.stream()
                .map(student -> new StudentReportDTO(
                        student.getCurp(),
                        student.getFullName(),
                        student.isHasBirthCertificate(),
                        student.isHasCurp(),
                        student.isHasCertificate(),
                        student.isHasCertificateStudiesImmediatelyPreceding(),
                        student.isHasEquivalenceStudies(),
                        student.isHasRevalidationStudies(),
                        student.isHasDegree(),
                        student.isHasCedula(),
                        // TODO evitar que regrese null
                        student.getComments() != null ? student.getComments() : ""))
                .toList();



        Path fileImages = Paths.get("src/main/resources/templates/report/").toAbsolutePath().normalize();
        File reportFile = ResourceUtils.getFile("classpath:templates/report/cuadro-inscripcion.jrxml");
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(studentReportDTOList);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name_institution", "Escuela Superior de Ciencias y Humanidades");
        parameters.put("imageDir", "classpath:static/images/reports/");
        parameters.put("name_degree", reportInscriptionDTO.name_degree());
        parameters.put("key", reportInscriptionDTO.key());
        parameters.put("shift", reportInscriptionDTO.shift());
        parameters.put("grade", reportInscriptionDTO.grade());
        parameters.put("group", reportInscriptionDTO.group());
        parameters.put("school_cycle", reportInscriptionDTO.school_cycle());
        parameters.put("name_zone", reportInscriptionDTO.name_zone());
        parameters.put("municipality", reportInscriptionDTO.municipality());
        parameters.put("modality", reportInscriptionDTO.modality());
        parameters.put("rvoe", reportInscriptionDTO.rvoe());
        parameters.put("date_rvoe", reportInscriptionDTO.date_rvoe());
        parameters.put("day", day);
        parameters.put("month", month);
        parameters.put("year", year);
        parameters.put("TABLE_DATA_SOURCE", dataSource);

        System.out.println("path " + reportFile.getAbsolutePath());

        JasperReport report = JasperCompileManager.compileReport(reportFile.getAbsolutePath());
        JasperPrint jp = JasperFillManager.fillReport(report, parameters, dataSource);

        if (jp != null) {
            JasperExportManager.exportReportToPdfFile(jp, fileImages.toFile().getAbsolutePath() + "/report.pdf");
            System.out.println("Report generated successfully.");
        } else {
            System.out.println("The report was generated but is empty.");
        }
    }
}
