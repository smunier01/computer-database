package com.excilys.cdb.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * my custom tag.
 *
 * @author excilys
 */
public class PaginationTag extends SimpleTagSupport {

    private int current;

    private int count;

    /**
     * main method for the tag.
     */
    @Override
    public void doTag() throws JspException, IOException {

        final StringBuilder builder = new StringBuilder();

        final JspWriter out = this.getJspContext().getOut();

        final String start;
        final String end;

        if (this.current == 0) {
            start = "<li class=\"disabled\"><a aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span></a></li>";
        } else {
            start = "<li><a href=\"?page=" + (this.current - 1)
                    + "\" aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span></a></li>";
        }

        if (this.current == (this.count - 1)) {
            end = "<li class=\"disabled\"><a aria-label=\"Next\"> <span aria-hidden=\"true\">&laquo;</span></a></li>";
        } else {
            end = "<li><a href=\"?page=" + (this.current + 1)
                    + "\" aria-label=\"Next\"> <span aria-hidden=\"true\">&laquo;</span></a></li>";
        }

        builder.append(start);

        final int indexStart = this.current - 5 >= 0 ? this.current - 5 : 0;
        final int indexStop = this.current + 5 <= (this.count - 1) ? this.current + 5 : this.count - 1;

        if (indexStart > 0) {
            builder.append("<li><a href=\"?page=0\">0</a></li>");
            builder.append("<li class=\"disabled\"><a>&hellip;</a></li>");
        }

        for (int i = indexStart; i <= indexStop; i++) {
            if (this.current == i) {
                builder.append("<li class=\"active\"><a href=\"?page=" + i + "\"> " + (i + 1) + " </a></li>");
            } else {
                builder.append("<li><a href=\"?page=" + i + "\">" + (i + 1) + "</a></li>");
            }
        }

        if (indexStop < (this.count - 1)) {
            builder.append("<li class=\"disabled\"><a>&hellip;</a></li>");
            builder.append("<li><a href=\"?page=" + (this.count - 1) + "\">" + this.count + "</a></li>");
        }

        builder.append(end);

        out.println(builder.toString());
    }

    /**
     * @return the current
     */
    public int getCurrent() {
        return this.current;
    }

    /**
     * @param current
     *            the current to set
     */
    public void setCurrent(final int current) {
        this.current = current;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return this.count;
    }

    /**
     * @param count
     *            the count to set
     */
    public void setCount(final int count) {
        this.count = count;
    }

}