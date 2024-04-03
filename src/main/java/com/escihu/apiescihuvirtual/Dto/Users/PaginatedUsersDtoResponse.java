package com.escihu.apiescihuvirtual.Dto.Users;

import lombok.Builder;

import java.util.List;

@Builder
public class PaginatedUsersDtoResponse {

    private List<UserDtoResponse> users;
    private int currentPage;
    private int totalPages;
    private int pageSize;

    public PaginatedUsersDtoResponse(List<UserDtoResponse> users, int currentPage, int totalPages, int pageSize) {
        this.users = users;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
    }

    public PaginatedUsersDtoResponse() {
    }

    public List<UserDtoResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserDtoResponse> users) {
        this.users = users;
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
