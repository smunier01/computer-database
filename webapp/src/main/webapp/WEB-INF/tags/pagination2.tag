<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag body-content="empty" %>
<%@ attribute name="current" required="true" %>
<%@ attribute name="count" required="true" %>
<%@ attribute name="psize" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags" %>

<ul class="pagination" style="margin-left: 140px;">

    <!-- Begin & Previous -->
    <c:choose>
        <c:when test="${current == 0}">
            <li class="disabled">
                <a id="begin" aria-label="Begin">
                    <span class="glyphicon glyphicon-step-backward" aria-hidden="true"></span>
                </a>
            </li>
            <li class="disabled">
                <a id="previous" aria-label="Previous">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                </a>
            </li>
        </c:when>
        <c:otherwise>
            <li>
                <a href="?pageNumber=${0}&size=${psize}&search=${param.search}&searchType=${param.searchType}&order=${param.order}&direction=${param.direction}"
                   aria-label="Begin">
                    <span class="glyphicon glyphicon-step-backward" aria-hidden="true"></span>
                </a>
            </li>
            <li>
                <a href="?pageNumber=${current - 1}&size=${psize}&search=${param.search}&searchType=${param.searchType}&order=${param.order}&direction=${param.direction}"
                   aria-label="Previous">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                </a>
            </li>
        </c:otherwise>
    </c:choose>

    <!-- Paginnation -->

    <c:choose>
        <%-- More than 10 pages --%>
        <c:when test="${count > 10}">
            <%-- Begin page assignation --%>
            <c:set var="begin" value="0"/>
            <c:if test="${(current - 5) >= 0}">
                <c:set var="begin" value="${current - 5}"/>
            </c:if>

            <%-- End page assignation --%>
            <c:choose>
                <c:when test="${begin == 0}">
                    <c:set var="end" value="${(count < 11) ? count - 1 : 10}"/>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${count - 1 < (current + 5)}">
                            <c:set var="begin" value="${(count - 11)}"/>
                            <c:set var="end" value="${(count - 1)}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="end" value="${(current + 5 > count) ? count : current + 5}"/>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>

            <%-- Display buttons --%>
            <c:forEach var="i" begin="${begin}" end="${end}">
                <li ${current == i ? 'class="active"' : ''}>
                    <a style="min-width:50px;" href="?pageNumber=${i}&size=${psize}&search=${param.search}&searchType=${param.searchType}&order=${param.order}&direction=${param.direction}">
                            ${i + 1}
                    </a>
                </li>
            </c:forEach>
        </c:when>
        <%-- Less than 10 pages --%>
        <c:otherwise>
            <c:forEach var="i" begin="0" end="${count - 1}">
                <li ${current == i ? 'class="active"' : ''}>
                    <a href="?pageNumber=${i}&size=${psize}&search=${param.search}&searchType=${param.searchType}&order=${param.order}&direction=${param.direction}">
                            ${i + 1}
                    </a>
                </li>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    <!-- Next & End-->
    <c:choose>
        <c:when test="${current == (count - 1)}">
            <li class="disabled">
                <a aria-label="Next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                </a>
            </li>
            <li class="disabled">
                <a aria-label="End">
                    <span class="glyphicon glyphicon-step-forward" aria-hidden="true"></span>
                </a>
            </li>
        </c:when>
        <c:otherwise>
            <li>
                <a id="next"
                   href="?pageNumber=${current + 1}&size=${psize}&search=${param.search}&searchType=${param.searchType}&order=${param.order}&direction=${param.direction}"
                   aria-label="Next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                </a>
            </li>
            <li>
                <a id="end"
                   href="?pageNumber=${count - 1}&size=${psize}&search=${param.search}&searchType=${param.searchType}&order=${param.order}&direction=${param.direction}"
                   aria-label="End">
                    <span class="glyphicon glyphicon-step-forward" aria-hidden="true"></span>
                </a>
            </li>
        </c:otherwise>
    </c:choose>

</ul>

<div class="btn-group btn-group-sm pull-right" role="group">
    <a href="?size=10" class="btn btn-default">10</a>
    <a href="?size=50" class="btn btn-default">50</a>
    <a href="?size=100" class="btn btn-default">100</a>
</div>
