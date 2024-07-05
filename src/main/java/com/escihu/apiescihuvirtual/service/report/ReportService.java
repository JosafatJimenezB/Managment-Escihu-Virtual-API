package com.escihu.apiescihuvirtual.service.report;

import com.escihu.apiescihuvirtual.Dto.ReportInscriptionDTO;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface ReportService {

    void generateEnrollmentReport(ReportInscriptionDTO reportInscriptionDTO) throws FileNotFoundException, JRException;


}
