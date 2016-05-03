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

    private final Direction direction;

    /**
     * default constructor.
     */
    public PageParameters() {
        size = 10;
        pageNumber = 0;
        search = "";
        order = Order.NAME;
        direction = Direction.ASC;
    }

    public PageParameters(final long size, final long pageNumber, final String search, final Order order,
            final Direction direction) {
        this.size = size;
        this.pageNumber = pageNumber;
        this.search = search;
        this.order = order;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "PageParameters [size=" + size + ", pageNumber=" + pageNumber + ", search=" + search
                + ", order=" + order + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((order == null) ? 0 : order.hashCode());
        result = (prime * result) + (int) (pageNumber ^ (pageNumber >>> 32));
        result = (prime * result) + ((search == null) ? 0 : search.hashCode());
        result = (prime * result) + (int) (size ^ (size >>> 32));
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final PageParameters other = (PageParameters) obj;
        if (order != other.order) {
            return false;
        }
        if (pageNumber != other.pageNumber) {
            return false;
        }
        if (search == null) {
            if (other.search != null) {
                return false;
            }
        } else if (!search.equals(other.search)) {
            return false;
        }
        if (size != other.size) {
            return false;
        }
        return true;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    /**
     * increment the page number.
     *
     * @return new page number
     */
    public long incPage() {
        return ++pageNumber;
    }

    /**
     * decrement the page number.
     *
     * @return new page number
     */
    public long decPage() {
        return pageNumber > 0 ? pageNumber : 0;
    }

    public static class Builder {
        private int size;
        private int pageNumber;
        private String search;
        private Order order;
        private Direction direction;

        public Builder size(final int size) {
            this.size = size;
            return this;
        }

        public Builder pageNumber(final int pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }

        public Builder search(final String search) {
            this.search = search;
            return this;
        }

        public Builder order(final Order order) {
            this.order = order;
            return this;
        }

        public Builder direction(final Direction direction) {
            this.direction = direction;
            return this;
        }

        public PageParameters build() {
            return new PageParameters(size, pageNumber, search, order, direction);
        }
    }

    public long getSize() {
        return size;
    }

    public void setSize(final long size) {
        this.size = size;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(final long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Direction getDirection() {
        return direction;
    }

}
