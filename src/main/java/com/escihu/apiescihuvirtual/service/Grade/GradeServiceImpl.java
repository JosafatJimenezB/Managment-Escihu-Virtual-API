package com.escihu.apiescihuvirtual.service.Grade;

import com.escihu.apiescihuvirtual.persistence.Entity.Grade.Grade;
import com.escihu.apiescihuvirtual.persistence.Entity.Grade.GradeDetail;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;
import com.escihu.apiescihuvirtual.persistence.Repository.GradeDetailRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.GradeRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.StudentRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GradeServiceImpl implements GradeService{

    private final GradeRepository gradeRepository;
    private final SubjectRepository subjectRepository;
    private final GradeDetailRepository gradeDetailRepository;

    public GradeServiceImpl(GradeRepository gradeRepository, SubjectRepository subjectRepository, GradeDetailRepository gradeDetailRepository) {
        this.gradeRepository = gradeRepository;
        this.subjectRepository = subjectRepository;
        this.gradeDetailRepository = gradeDetailRepository;
    }

    @Override
    public void addGrade(Grade grade) {
        gradeRepository.save(grade);
    }

    @Override
    public Grade getGradeById(long id) {
        return gradeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Grade> getGradesBySubject(Subject subject) {
        return gradeRepository.findBySubject(subject);
    }

    @Override
    public List<Grade> getGradesBySubjectId(long subjectId) {
        Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
        if (subjectOptional.isPresent()) {
            return gradeRepository.findBySubject(subjectOptional.get());
        } else {
            return null;
        }
    }

    @Override
    public Grade updateGrade(Grade gradeForm) {
        Optional<Grade> existingGrade = gradeRepository.findById(gradeForm.getId());

        if (existingGrade.isPresent()) {
            Grade grade = existingGrade.get();
            grade.setDescription(gradeForm.getDescription());
            return gradeRepository.save(grade);
        } else {
            return null;
        }
    }

    @Override
    public void deleteGradeById(long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public void deleteGrade(Grade grade) {
        gradeRepository.delete(grade);
    }

    @Override
    public List<GradeDetail> getGradeScorebook(long gradeId) {
        List<GradeDetail> scorebook = new ArrayList<>();
        Optional<Grade> gradeOptional = gradeRepository.findById(gradeId);
        if (gradeOptional.isPresent()) {
            Grade grade = gradeOptional.get();
            List<Student> students = grade.getSubject().getCourse().getStudents();

            for (Student student : students) {
                Optional<GradeDetail> scoreOptional = gradeDetailRepository.findByGradeAndStudent(grade, student);

                GradeDetail score = scoreOptional.orElseGet(() -> {
                    GradeDetail nscore = new GradeDetail();
                    nscore.setStudent(student);
                    nscore.setGrade(grade);
                    return nscore;
                });
                scorebook.add(score);
            }
        } else {
            return null;
        }

        return scorebook;
    }
}
