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
        return gradeRepository.getOne(id);
    }

    @Override
    public List<Grade> getGradesBySubject(Subject subject) {
        return gradeRepository.findBySubject(subject);
    }

    @Override
    public List<Grade> getGradesBySubjectId(long subjectId) {
        Subject subject = subjectRepository.getOne(subjectId);
        return gradeRepository.findBySubject(subject);
    }

    @Override
    public Grade updateGrade(Grade gradeForm) {
        Grade grade = gradeRepository.getOne(gradeForm.getId());
        grade.setDescription(gradeForm.getDescription());
        gradeRepository.save(grade);
        return grade;
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
        Grade grade = gradeRepository.getOne(gradeId);
        List<Student> students = grade.getSubject().getCourse().getStudents();

        for (Student student : students) {
            GradeDetail score = gradeDetailRepository.findByGradeAndStudent(grade, student).orElse(null);
            if (score == null) {
                GradeDetail nscore = new GradeDetail();
                nscore.setStudent(student);
                nscore.setGrade(grade);
                scorebook.add(nscore);
                continue;
            }
            scorebook.add(score);
        }

        return scorebook;
    }
}
