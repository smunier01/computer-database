package com.excilys.cdb.util;

/**
 * Class to help specifiying page parameters.
 *
 * @author excilys
 */
public class PageParameters {

    /**
     * size of the page.
     */
    private long size;

    /**
     * number of the page.
     */
    private long pageNumber;

    /**
     * default constructor.
     */
    public PageParameters() {
        this.size = 10;
        this.pageNumber = 0;
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

    public long getSize() {
        return this.size;
    }

    public void setSize(final long size) {
        this.size = size;
    }

    public long getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(final long pageNumber) {
        this.pageNumber = pageNumber;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (this.pageNumber ^ (this.pageNumber >>> 32));
        result = prime * result + (int) (this.size ^ (this.size >>> 32));
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
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
        if (this.pageNumber != other.pageNumber) {
            return false;
        }
        if (this.size != other.size) {
            return false;
        }
        return true;
    }

}
