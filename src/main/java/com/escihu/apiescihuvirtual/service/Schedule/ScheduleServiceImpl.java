package com.escihu.apiescihuvirtual.service.Schedule;

import com.escihu.apiescihuvirtual.persistence.Entity.Course.Course;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.SubjectSchedule;
import com.escihu.apiescihuvirtual.persistence.Repository.CourseRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;
    private final CourseRepository courseRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, CourseRepository courseRepository) {
        this.scheduleRepository = scheduleRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void save(SubjectSchedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    public Optional<SubjectSchedule> getScheduleById(long id) {
        return scheduleRepository.findById(id);
    }

    @Override
    public void deleteSchedule(SubjectSchedule schedule) {
        scheduleRepository.delete(schedule);
    }

    @Override
    public HashMap<String, List<SubjectSchedule>> getScheduleByCourseId(long courseId) {
        Course course = courseRepository.getOne(courseId);
        List<SubjectSchedule> monday = scheduleRepository.findByDayOfWeekAndSubjectInOrderByStartTimeAsc(1, course.getSubjects());
        List<SubjectSchedule> tuesday = scheduleRepository.findByDayOfWeekAndSubjectInOrderByStartTimeAsc(2, course.getSubjects());
        List<SubjectSchedule> wednesday = scheduleRepository.findByDayOfWeekAndSubjectInOrderByStartTimeAsc(3, course.getSubjects());
        List<SubjectSchedule> thursday = scheduleRepository.findByDayOfWeekAndSubjectInOrderByStartTimeAsc(4, course.getSubjects());
        List<SubjectSchedule> friday = scheduleRepository.findByDayOfWeekAndSubjectInOrderByStartTimeAsc(5, course.getSubjects());
        List<SubjectSchedule> saturday = scheduleRepository.findByDayOfWeekAndSubjectInOrderByStartTimeAsc(6, course.getSubjects());
        List<SubjectSchedule> sunday = scheduleRepository.findByDayOfWeekAndSubjectInOrderByStartTimeAsc(7, course.getSubjects());
        HashMap<String, List<SubjectSchedule>> schedule = new HashMap<>();
        schedule.put("monday", monday);
        schedule.put("tuesday", tuesday);
        schedule.put("wednesday", wednesday);
        schedule.put("thursday", thursday);
        schedule.put("friday", friday);
        schedule.put("saturday", saturday);
        schedule.put("sunday", sunday);
        return schedule;
    }
}
