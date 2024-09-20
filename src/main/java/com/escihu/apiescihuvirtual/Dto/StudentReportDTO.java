package com.escihu.apiescihuvirtual.Dto;

import lombok.Getter;

@Getter
// Evitar Cambiar esta clase de un Record ya que jasper no soporta records
public class StudentReportDTO {
    private final String curp;
    private final String fullName;
    private final boolean hasBirthCertificate;
    private final boolean hasCurp;
    private final boolean hasCertificate;
    private final boolean hasCertificateStudiesImmediatelyPreceding;
    private final boolean hasEquivalenceStudies;
    private final boolean hasRevalidationStudies;
    private final boolean hasDegree;
    private final boolean hasCedula;
    private final String comments;

    public StudentReportDTO(String curp, String fullName, boolean hasBirthCertificate, boolean hasCurp, boolean hasCertificate, boolean hasCertificateStudiesImmediatelyPreceding, boolean hasEquivalenceStudies, boolean hasRevalidationStudies, boolean hasDegree, boolean hasCedula, String comments) {
        this.curp = curp;
        this.fullName = fullName;
        this.hasBirthCertificate = hasBirthCertificate;
        this.hasCurp = hasCurp;
        this.hasCertificate = hasCertificate;
        this.hasCertificateStudiesImmediatelyPreceding = hasCertificateStudiesImmediatelyPreceding;
        this.hasEquivalenceStudies = hasEquivalenceStudies;
        this.hasRevalidationStudies = hasRevalidationStudies;
        this.hasDegree = hasDegree;
        this.hasCedula = hasCedula;
        this.comments = comments;
    }

}
