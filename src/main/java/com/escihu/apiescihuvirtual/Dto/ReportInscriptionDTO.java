package com.escihu.apiescihuvirtual.Dto;


public record ReportInscriptionDTO(
        //TODO cambiar name_degree a nameDegree
        // - Esto tambien se debe cambiar en el reporte jxml
        String name_degree,
        String key,
        String shift,
        String grade,
        String group,
        //TODO: Cambiar school_cycle a schoolCycle
        String school_cycle,
        //TODO: Cambiar name_zone a nameZone
        String name_zone,
        String municipality,
        String modality,
        String rvoe,
        //TODO: Cambiar a tipo Date
        String date_rvoe
) { }
