package com.escihu.apiescihuvirtual.service.Attendance;

import com.escihu.apiescihuvirtual.Dto.Attendance.AttendaceDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Attendance.Attendance;

import java.util.List;

public interface AttendaceService {

    public Attendance register(AttendaceDtoRequest attendaceDtoRequest);

    public List<Attendance> listAll();
}
