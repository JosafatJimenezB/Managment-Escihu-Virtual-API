package com.escihu.apiescihuvirtual.service.Attendance;

import com.escihu.apiescihuvirtual.Dto.Attendance.AttendaceDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Attendance.Attendance;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.escihu.apiescihuvirtual.persistence.Repository.AttendaceRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendaceService {

    private final AttendaceRepository attendaceRepository;
    private final UserRepository userRepository;

    public AttendanceServiceImpl(AttendaceRepository attendaceRepository, UserRepository userRepository) {
        this.attendaceRepository = attendaceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Attendance register(AttendaceDtoRequest attendaceDtoRequest) {

        Attendance attendance = new Attendance();
        attendance.setTypeAttendace(attendaceDtoRequest.getTypeAttendace());

        Optional<User> user = userRepository.findById(attendaceDtoRequest.getUserId());

        user.ifPresent(attendance::setUser);

        return attendaceRepository.save(attendance);
    }

    @Override
    public Page<Attendance> attendancePagination(Pageable pageable) {
        return attendaceRepository.findAll(pageable);
    }

    @Override
    public List<Attendance> listAll() {
        return attendaceRepository.findAll();
    }

    @Override
    public Page<Attendance> attendaceByUserId(Long userId, Pageable pageable) {
        return attendaceRepository.findByUserUserId(userId, pageable);
    }
}
