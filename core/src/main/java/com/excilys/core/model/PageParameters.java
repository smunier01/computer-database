package com.excilys.core.model;

/**
 * Class to help specifiying page parameters.
 *
 * @author excilys
 */
public class PageParameters {

    public enum Order {
        NAME, COMPANY_NAME, INTRODUCED, DISCONTINUED;
    }

    public enum Direction {
        ASC, DESC;
    }

    private long size;

    private long pageNumber;

    private String search;
    private String searchType = "";

    private Order order;
    private Direction direction;

    /**
     * default constructor.
     */
    public PageParameters() {
        this.size = 10;
        this.pageNumber = 0;
        this.search = "";
        this.searchType = "";
        this.order = Order.NAME;
        this.direction = Direction.ASC;
    }

    /**
     * PageParameters constructor.
     *
     * @param size       size of a page.
     * @param pageNumber current page number.
     * @param search     string search for the query.
     * @param searchType string search type for search by company or computer names.
     * @param order      enum corresponding to the column name for the order by.
     * @param direction  direction of the order by (asc, desc).
     */
    public PageParameters(long size, long pageNumber, String search, String searchType, Order order, Direction direction) {
        this.size = size;
        this.pageNumber = pageNumber;
        this.search = search;
        this.searchType = searchType;
        this.order = order;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "PageParameters [size=" + this.size + ", pageNumber=" + this.pageNumber + ", search=" + this.search
                + ", order=" + this.order + "]";
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

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * increment the page number.
     *
     * @return new page number
     */
    public long incPage() {
        return ++this.pageNumber;
    }

    /**
     * decrement the page number.
     *
     * @return new page number
     */
    public long decPage() {
        return this.pageNumber > 0 ? this.pageNumber : 0;
    }

    /**
     * Builder pattern for the page parameters.
     */
    public static class Builder {
        private int size = 10;
        private int pageNumber = 0;
        private String search = "";
        private String searchType = "";
        private Order order = Order.NAME;
        private Direction direction = Direction.ASC;

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder pageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }

        public Builder search(String search) {
            this.search = search;
            return this;
        }

        public Builder searchType(String searchType) {
            this.searchType = searchType;
            return this;
        }

        public Builder order(Order order) {
            this.order = order;
            return this;
        }

        public Builder direction(Direction direction) {
            this.direction = direction;
            return this;
        }

        public PageParameters build() {
            return new PageParameters(this.size, this.pageNumber, this.search, this.searchType, this.order, this.direction);
        }
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageParameters that = (PageParameters) o;

        if (size != that.size) return false;
        if (pageNumber != that.pageNumber) return false;
        if (search != null ? !search.equals(that.search) : that.search != null) return false;
        if (order != that.order) return false;
        return direction == that.direction;

    }

    @Override
    public int hashCode() {
        int result = (int) (size ^ (size >>> 32));
        result = 31 * result + (int) (pageNumber ^ (pageNumber >>> 32));
        result = 31 * result + (search != null ? search.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }
}
