package com.excilys.core.dto;

import com.excilys.core.model.PageParameters;

public class PageParametersDTO {

    // list of variables
    private int size = 10;
    private int pageNumber = 0;
    private String search = "";
    private String searchType = "";
    private String order = "name";
    private String direction = "asc";

    /**
     * Default constructor.
     */
    public PageParametersDTO() {
    }

    /**
     * Contructor of the PageParameterDTO.
     *
     * @param params to create the DTO
     */
    public PageParametersDTO(PageParameters params) {
        this.size = (int) params.getSize();
        this.pageNumber = (int) params.getPageNumber();
        this.search = params.getSearch();
        this.searchType = params.getSearchType();
        this.order = params.getOrder().toString();
        this.direction = params.getDirection().toString();
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public int hashCode() {
        int result = size;
        result = 31 * result + pageNumber;
        result = 31 * result + (search != null ? search.hashCode() : 0);
        result = 31 * result + (searchType != null ? searchType.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PageParametersDTO that = (PageParametersDTO) o;
        if (size != that.size) {
            return false;
        }
        if (pageNumber != that.pageNumber) {
            return false;
        }
        if (search != null ? !search.equals(that.search) : that.search != null) {
            return false;
        }
        if (searchType != null ? !searchType.equals(that.search) : that.searchType != null) {
            return false;
        }
        if (order != null ? !order.equals(that.order) : that.order != null) {
            return false;
        }
        return direction != null ? direction.equals(that.direction) : that.direction == null;
    }

    @Override
    public String toString() {
        return "PageParametersDTO [size=" + this.size + ", pageNumber=" + this.pageNumber + ", search=" + this.search + ", order=" + this.order + ", direction=" + this.direction + "]";
    }

}
