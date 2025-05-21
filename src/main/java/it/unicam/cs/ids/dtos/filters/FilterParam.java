package it.unicam.cs.ids.dtos.filters;

import it.unicam.cs.ids.dtos.DTO;
import it.unicam.cs.ids.enums.SortDirection;

import java.util.List;

public abstract class FilterParam extends DTO {
    private String page;
    private String pageSize;
    private SortDirection sortDirection;
    private String sortBy;
    private String searchBy;
    private List<String> tags;

    public FilterParam(String page, String pageSize, SortDirection sortDirection, String sortBy, String searchBy, List<String> tags) {
        this.page = page;
        this.pageSize = pageSize;
        this.sortDirection = sortDirection;
        this.sortBy = sortBy;
        this.searchBy = searchBy;
        this.tags = tags;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
