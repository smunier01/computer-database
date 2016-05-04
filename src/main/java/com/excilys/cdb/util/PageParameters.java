package com.excilys.cdb.util;

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

    private Order order;

    private Direction direction;

    /**
     * default constructor.
     */
    public PageParameters() {
        this.size = 10;
        this.pageNumber = 0;
        this.search = "";
        this.order = Order.NAME;
        this.direction = Direction.ASC;
    }

    public PageParameters(long size, long pageNumber, String search, Order order, Direction direction) {
        this.size = size;
        this.pageNumber = pageNumber;
        this.search = search;
        this.order = order;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "PageParameters [size=" + this.size + ", pageNumber=" + this.pageNumber + ", search=" + this.search
                + ", order=" + this.order + "]";
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.order == null) ? 0 : this.order.hashCode());
        result = (prime * result) + (int) (this.pageNumber ^ (this.pageNumber >>> 32));
        result = (prime * result) + ((this.search == null) ? 0 : this.search.hashCode());
        result = (prime * result) + (int) (this.size ^ (this.size >>> 32));
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
        PageParameters other = (PageParameters) obj;
        if (this.order != other.order) {
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

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search) {
        this.search = search;
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

    public static class Builder {
        private int size = 10;
        private int pageNumber = 0;
        private String search = "";
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

        public Builder order(Order order) {
            this.order = order;
            return this;
        }

        public Builder direction(Direction direction) {
            this.direction = direction;
            return this;
        }

        public PageParameters build() {
            return new PageParameters(this.size, this.pageNumber, this.search, this.order, this.direction);
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

}
