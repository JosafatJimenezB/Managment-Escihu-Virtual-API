package com.escihu.apiescihuvirtual.Dto.Student;

import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import lombok.Builder;

import java.util.List;

@Builder
public class PaginatedStudentDtoResponse {

    private List<StudentDtoResponse> students;
    private int currentPage;
    private int totalPages;
    private int pageSize;

    public PaginatedStudentDtoResponse(List<StudentDtoResponse> students, int currentPage, int totalPages, int pageSize) {
        this.students = students;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
    }

     public PaginatedStudentDtoResponse(){

     }

    public List<StudentDtoResponse> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDtoResponse> students) {
        this.students = students;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
