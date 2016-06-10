package com.excilys.core.model;

import java.util.List;

/**
 * generic page containing a list of object.
 *
 * @param <T>
 */
public class Page<T> {

    // list of the variables
    private List<T> list;
    private Long totalCount;
    private PageParameters params;

    /**
     * returns the number of pages necessary for the pagination.
     *
     * @return number of pages.
     */
    public long numberOfPages() {
        return Math.max(1, ((this.totalCount + this.params.getSize()) - 1) / this.params.getSize());
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public PageParameters getParams() {
        return this.params;
    }

    public void setParams(PageParameters params) {
        this.params = params;
    }

    public static class Builder<T> {
        Page<T> page;

        /**
         * Default Construtor.
         */
        public Builder() {
            this.page = new Page<T>();
        }

        /**
         * Set the list.
         *
         * @param list to set
         * @return the builder
         */
        public Builder<T> list(List<T> list) {
            this.page.list = list;
            return this;
        }

        /**
         * Set the count.
         *
         * @param count to set
         * @return the builder
         */
        public Builder<T> totalCount(Long count) {
            this.page.totalCount = count;
            return this;
        }

        /**
         * Set the params.
         *
         * @param params to set
         * @return the builder
         */
        public Builder<T> params(PageParameters params) {
            this.page.params = params;
            return this;
        }

        /**
         * Get the object.
         *
         * @return an object build by the builder.
         */
        public Page<T> build() {
            return this.page;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Page<?> page = (Page<?>) o;

        if (list != null ? !list.equals(page.list) : page.list != null) {
            return false;
        }
        if (totalCount != null ? !totalCount.equals(page.totalCount) : page.totalCount != null) {
            return false;
        }
        return params != null ? params.equals(page.params) : page.params == null;

    }

    @Override
    public int hashCode() {
        int result = list != null ? list.hashCode() : 0;
        result = 31 * result + (totalCount != null ? totalCount.hashCode() : 0);
        result = 31 * result + (params != null ? params.hashCode() : 0);
        return result;
    }
}
