package com.excilys.cdb.util;

/**
 * Class to help specifiying page parameters.
 *
 * @author excilys
 */
public class PageParameters {

    public enum Order {
        NAME, COMPANY_NAME, INTRODUCED_DATE, DISCONTINUED_DATE;
    }

    private long size;

    private long pageNumber;

    private String search;

    private Order order;

    /**
     * default constructor.
     */
    public PageParameters() {
        size = 10;
        pageNumber = 0;
        search = null; // @TODO it should probably be empty string
        order = Order.NAME;
    }

    /**
     * page parameters constructor.
     *
     * @param size
     *            max size of the page
     * @param pageNumber
     *            current page number
     */
    public PageParameters(final long size, final long pageNumber) {
        this.size = size;
        this.pageNumber = pageNumber;
        search = null;
        order = Order.NAME;
    }

    public PageParameters(final long size, final long pageNumber, final String search) {
        this.size = size;
        this.pageNumber = pageNumber;
        this.search = search;
        order = Order.NAME;
    }

    public PageParameters(final long size, final long pageNumber, final String search, final Order order) {
        this.size = size;
        this.pageNumber = pageNumber;
        this.search = search;
        this.order = order;
    }

    @Override
    public String toString() {
        return "PageParameters [size=" + size + ", pageNumber=" + pageNumber + ", search=" + search + ", order=" + order
                + "]";
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PageParameters other = (PageParameters) obj;
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

    public void setSearch(String search) {
        this.search = search;
    }

    public Order getOrder() {
        return order;
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

    public class Builder {
        private int size;
        private int pageNumber;
        private String search;
        private Order order;

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

        public PageParameters build() {
            return new PageParameters(size, pageNumber, search, order);
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

}
