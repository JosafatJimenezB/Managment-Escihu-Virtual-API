package com.escihu.apiescihuvirtual.persistence.Repository;

import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.SubjectSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<SubjectSchedule, Long> {

    public List<SubjectSchedule> findByDayOfWeekAndSubjectInOrderByStartTimeAsc(int dayOfWeek, List<Subject> subject);
}
