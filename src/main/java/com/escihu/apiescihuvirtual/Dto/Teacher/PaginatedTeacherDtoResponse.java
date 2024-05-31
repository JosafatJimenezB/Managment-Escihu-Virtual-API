package com.escihu.apiescihuvirtual.Dto.Teacher;

import lombok.Builder;

import java.util.List;

@Builder
public class PaginatedTeacherDtoResponse {

    private List<TeacherDtoResponse> teachers;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private int totalElements;

    public PaginatedTeacherDtoResponse(List<TeacherDtoResponse> teachers, int currentPage, int totalPages, int pageSize, int totalElements) {
        this.teachers = teachers;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }

    public PaginatedTeacherDtoResponse() {

    }

    public List<TeacherDtoResponse> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherDtoResponse> teachers) {
        this.teachers = teachers;
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

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}
