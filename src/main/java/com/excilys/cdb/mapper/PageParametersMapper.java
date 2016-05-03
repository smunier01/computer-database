package com.excilys.cdb.mapper;

import static com.excilys.cdb.util.PageParameters.Order;
import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.util.PageParameters;
import com.excilys.cdb.util.Util;
import com.excilys.cdb.util.PageParameters.Direction;

public enum PageParametersMapper {
    INSTANCE;

    public static PageParametersMapper getInstance() {
        return INSTANCE;
    }

    public PageParameters map(final HttpServletRequest request) {

        final int page = Util.getInt(request, "page", 0);

        final int psize = Util.getInt(request, "psize", 10);

        final String search = request.getParameter("search") == null ? "" : request.getParameter("search");

        final String orderStr = request.getParameter("order");

        Order order = Order.NAME;

        if (orderStr != null) {
            order = Order.valueOf(orderStr.toUpperCase());
        }

        Direction direction = Direction.ASC;

        final String dirStr = request.getParameter("dir");

        if (dirStr != null) {
            direction = Direction.valueOf(dirStr.toUpperCase());
        }

        return new PageParameters(psize, page, search, order, direction);

    }
}
