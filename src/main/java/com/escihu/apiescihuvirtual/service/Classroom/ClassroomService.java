package com.escihu.apiescihuvirtual.service.Classroom;

import com.escihu.apiescihuvirtual.Dto.ClassroomDto;
import com.escihu.apiescihuvirtual.persistence.Entity.Classroom.Classroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClassroomService {

    public Classroom addClassroom(ClassroomDto classroomDto);

    public Classroom updateClassroom(Long id, ClassroomDto classroomDto);

    public Page<Classroom> getAllClassrooms(Pageable pageable);

    public List<Classroom> getAllClassrooms();

    public Classroom getClassroomById(long id);

    public boolean exists(Long id);
}
