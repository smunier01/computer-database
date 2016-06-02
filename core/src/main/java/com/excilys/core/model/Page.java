package com.excilys.core.model;

import java.util.List;

public class Page<T> {

    private List<T> list;

    private Long totalCount;

    private PageParameters params;

    public Page() {

    }

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

        public Builder() {
            this.page = new Page<T>();
        }

        public Builder<T> list(List<T> list) {
            this.page.list = list;
            return this;
        }

        public Builder<T> totalCount(Long count) {
            this.page.totalCount = count;
            return this;
        }

        public Builder<T> params(PageParameters params) {
            this.page.params = params;
            return this;
        }

        public Page<T> build() {
            return this.page;
        }
    }
}
