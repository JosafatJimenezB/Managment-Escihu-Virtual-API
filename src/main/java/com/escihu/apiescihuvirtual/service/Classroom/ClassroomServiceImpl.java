package com.escihu.apiescihuvirtual.service.Classroom;

import com.escihu.apiescihuvirtual.Dto.ClassroomDto;
import com.escihu.apiescihuvirtual.persistence.Entity.Classroom.Classroom;
import com.escihu.apiescihuvirtual.persistence.Repository.ClassroomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Override
    public Classroom addClassroom(ClassroomDto classroomDto) {

        Classroom classroom = Classroom.builder()
                .name(classroomDto.getName())
                .description(classroomDto.getDescription())
                .build();
        return classroomRepository.save(classroom);
    }

    @Override
    public Classroom updateClassroom(Long classroomId, ClassroomDto classroomDto) {
        Optional<Classroom> classroomExists = classroomRepository.findById(classroomId);

        if (!classroomExists.isPresent()) {
            return null;
        }

        Classroom classroom = classroomExists.get();

        classroom.setName(classroomDto.getName());
        classroom.setDescription(classroomDto.getDescription());

        return classroomRepository.save(classroom);
    }

    @Override
    public Page<Classroom> getAllClassrooms(Pageable pageable) {
        return classroomRepository.findAll(pageable);
    }

    @Override
    public Classroom getClassroomById(long classroomId) {
        return classroomRepository.findById(classroomId).orElse(null);
    }

    @Override
    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    @Override
    public boolean existsClassroom(Long classroomId) {
        return classroomRepository.existsById(classroomId);
    }
}
