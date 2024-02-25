package com.escihu.apiescihuvirtual.service.Dashboard;

import com.escihu.apiescihuvirtual.persistence.Entity.Course.Course;
import com.escihu.apiescihuvirtual.persistence.Entity.Subject.Subject;

import java.util.List;

public interface DashboardService {

    public int getTotalStudents();

    public int getTotalTeachers();

    public int getTotalClassrooms();

    public List<Course> getCoursesByTeacher(String username);

    public List<Subject> getSubjectsByTeacher(String username);
}
