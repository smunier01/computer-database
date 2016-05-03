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
            switch (orderStr) {
            case "name":
                order = Order.NAME;
                break;
            case "introduced":
                order = Order.INTRODUCED_DATE;
                break;
            case "discontinued":
                order = Order.DISCONTINUED_DATE;
                break;
            case "company":
                order = Order.COMPANY_NAME;
                break;
            default:
                order = Order.NAME;
                break;
            }
        }

        Direction direction = Direction.ASC;

        final String dirStr = request.getParameter("dir");
        if (dirStr != null) {
            switch (dirStr) {
            case "asc":
                direction = Direction.ASC;
                break;
            case "desc":
                direction = Direction.DESC;
                break;
            default:
                direction = Direction.ASC;
            }
        }

        return new PageParameters(psize, page, search, order, direction);

    }
}
