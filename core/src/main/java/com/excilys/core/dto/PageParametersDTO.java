package com.excilys.core.dto;

public class PageParametersDTO {

    private int size = 10;

    private int pageNumber = 0;

    private String search = "";

    private String order = "name";

    private String direction = "asc";

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
    public String toString() {
        return "PageParametersDTO [size=" + this.size + ", pageNumber=" + this.pageNumber + ", search=" + this.search + ", order=" + this.order + ", direction=" + this.direction + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.direction == null) ? 0 : this.direction.hashCode());
        result = prime * result + ((this.order == null) ? 0 : this.order.hashCode());
        result = prime * result + (this.pageNumber ^ (this.pageNumber >>> 32));
        result = prime * result + ((this.search == null) ? 0 : this.search.hashCode());
        result = prime * result + (this.size ^ (this.size >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        PageParametersDTO other = (PageParametersDTO) obj;
        if (this.direction == null) {
            if (other.direction != null) {
                return false;
            }
        } else if (!this.direction.equals(other.direction)) {
            return false;
        }
        if (this.order == null) {
            if (other.order != null) {
                return false;
            }
        } else if (!this.order.equals(other.order)) {
            return false;
        }
        if (this.pageNumber != other.pageNumber) {
            return false;
        }
        if (this.search == null) {
            if (other.search != null) {
                return false;
            }
        } else if (!this.search.equals(other.search)) {
            return false;
        }
        if (this.size != other.size) {
            return false;
        }
        return true;
    }

}
