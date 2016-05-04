package com.excilys.cdb.mapper;

import static com.excilys.cdb.util.PageParameters.Order;
import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.util.PageParameters;
import com.excilys.cdb.util.PageParameters.Direction;

public enum PageParametersMapper {
    INSTANCE;

    public static PageParametersMapper getInstance() {
        return INSTANCE;
    }

    public PageParameters map(HttpServletRequest request) {

        int page = getIntWithDefault(request, "page", 0);

        int psize = getIntWithDefault(request, "psize", 10);

        String search = request.getParameter("search") == null ? "" : request.getParameter("search");

        String orderStr = request.getParameter("order");

        Order order = Order.NAME;

        if (orderStr != null) {
            order = Order.valueOf(orderStr.toUpperCase());
        }

        Direction direction = Direction.ASC;

        String dirStr = request.getParameter("dir");

        if (dirStr != null) {
            direction = Direction.valueOf(dirStr.toUpperCase());
        }

        return new PageParameters(psize, page, search, order, direction);

    }

    public static int getIntWithDefault(final HttpServletRequest request, final String key, final int def) {
        final String str = request.getParameter(key);

        int result;

        if (str == null) {
            result = def;
        } else {
            try {
                result = Integer.parseInt(str);
            } catch (final NumberFormatException e) {
                result = def;
            }
        }

        return result;
    }
}
