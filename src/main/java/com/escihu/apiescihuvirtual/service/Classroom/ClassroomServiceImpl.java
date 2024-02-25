package com.escihu.apiescihuvirtual.service.Classroom;

import com.escihu.apiescihuvirtual.persistence.Entity.Classroom.Classroom;
import com.escihu.apiescihuvirtual.persistence.Repository.ClassroomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService{

    private final ClassroomRepository classroomRepository;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Override
    public void addClassroom(Classroom classroom) {
        classroomRepository.save(classroom);
    }

    @Override
    public void updateClassroom(Classroom classroomForm) {
        Classroom classroom = classroomRepository.getOne(classroomForm.getId());

        classroom.setName(classroomForm.getName());
        classroom.setDescription(classroomForm.getDescription());

        classroomRepository.save(classroom);
    }
    @Override
    public Page<Classroom> getAllClassrooms(Pageable pageable) {
        return classroomRepository.findAll(pageable);
    }

    @Override
    public Classroom getClassroomById(long id) {
        return classroomRepository.findById(id).orElse(null);
    }

    @Override
    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }
}
