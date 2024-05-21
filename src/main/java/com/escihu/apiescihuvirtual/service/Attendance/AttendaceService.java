package com.escihu.apiescihuvirtual.service.Attendance;

import com.escihu.apiescihuvirtual.Dto.Attendance.AttendaceDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Attendance.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AttendaceService {

    public Attendance register(AttendaceDtoRequest attendaceDtoRequest);

    public Page<Attendance> attendancePagination(Pageable pageable);

    public List<Attendance> listAll();

    public Page<Attendance> attendaceByUserId(Long userId, Pageable pageable);
}
