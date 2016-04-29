package com.excilys.cdb.mapper;

import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.util.PageParameters;
import com.excilys.cdb.util.Util;

public enum PageParametersMapper {
    INSTANCE;

    public static PageParametersMapper getInstance() {
        return INSTANCE;
    }

    public PageParameters map(HttpServletRequest request) {

        final int page = Util.getInt(request, "page", 0);

        final int psize = Util.getInt(request, "psize", 10);

        return new PageParameters(psize, page);

    }
}
