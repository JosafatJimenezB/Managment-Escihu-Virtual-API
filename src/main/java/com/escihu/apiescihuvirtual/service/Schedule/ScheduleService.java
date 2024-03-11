package com.escihu.apiescihuvirtual.service.Schedule;

import com.escihu.apiescihuvirtual.persistence.Entity.Subject.SubjectSchedule;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {

    public SubjectSchedule save(SubjectSchedule schedule);

    public Optional<SubjectSchedule> getScheduleById(long id);

    public void deleteSchedule(Long id);

    public HashMap<String, List<SubjectSchedule>> getScheduleByCourseId(long courseId);

    public boolean existById(Long id);
}
