package com.excilys.cdb.mapper;

import static com.excilys.cdb.model.PageParameters.Order;

import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.model.PageParameters;
import com.excilys.cdb.model.PageParameters.Direction;

/**
 * Implements different mapping methods to create or convert a PageParameters
 * objects.
 *
 * @author simon
 */
public enum PageParametersMapper {
    INSTANCE;

    public static PageParametersMapper getInstance() {
        return INSTANCE;
    }

    /**
     * map a HttpServletRequest to a PageParameters.
     *
     * TODO validation should be move to anothing method (like for computer &
     * company)
     *
     * @param request
     *            httpservletrequest to map.
     * @return page parameters object.
     */
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

    /**
     * helper to get an int inside a HttpServletRequest object.
     *
     * @param request
     *            object containing the int
     * @param key
     *            key where the int we want to get is
     * @param def
     *            value to return if they key doesn't exists
     * @return int, or def if key doesn't exists
     */
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
