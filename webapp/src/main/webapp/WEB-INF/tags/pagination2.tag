<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag body-content="empty" %>
<%@ attribute name="current" required="true" %>
<%@ attribute name="count" required="true" %>
<%@ attribute name="psize" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags" %>

<c:set var="indexStart" value="0"/>

<c:if test="${(current - 5) >= 0}">
    <c:set var="indexStart" value="${current - 5}"/>
</c:if>

<c:set var="indexStop" value="${count - 1}"/>

<c:if test="${(current + 5) <= (count - 1)}">
    <c:set var="indexStop" value="${current + 5}"/>
</c:if>

<ul class="pagination">

    <c:choose>
        <c:when test="${current == 0}">
            <li class="disabled"><a id="previous" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a></li>
        </c:when>
        <c:otherwise>
            <li><a href="?pageNumber=${current - 1}&size=${psize}&search=${param.search}&searchType=${param.searchType}&order=${param.order}&direction=${param.direction}" aria-label="Previous"> <span
                    aria-hidden="true">&laquo;</span></a></li>
        </c:otherwise>
    </c:choose>

    <c:if test="${indexStart > 0}">
        <li><a href="?pageNumber=0">1</a></li>
        <li class="disabled"><a>&hellip;</a></li>
    </c:if>

    <c:forEach var="i" begin="${indexStart}" end="${indexStop}">
        <li ${current == i ? 'class="active"' : ''}><a
                href="?pageNumber=${i}&size=${psize}&search=${param.search}&searchType=${param.searchType}&order=${param.order}&direction=${param.direction}">${i + 1}</a>
        </li>
    </c:forEach>

    <c:if test="${indexStop < (count - 1)}">
        <li class="disabled"><a>&hellip;</a></li>
        <li><a href="?pageNumber=${count - 1}&size=${psize}&search=${param.search}&searchType=${param.searchType}&order=${param.order}&dir=${param.dir}">${count}</a></li>
    </c:if>

    <c:choose>
        <c:when test="${current == (count - 1)}">
            <li class="disabled"><a aria-label="Next"> <span
                    aria-hidden="true">&laquo;</span></a></li>
        </c:when>
        <c:otherwise>
            <li><a id="next" href="?pageNumber=${current + 1}&size=${psize}&search=${param.search}&searchType=${param.searchType}&order=${param.order}&direction=${param.direction}" aria-label="Next">
                <span aria-hidden="true">&laquo;</span>
            </a></li>
        </c:otherwise>
    </c:choose>

</ul>

<div class="btn-group btn-group-sm pull-right" role="group">
    <a href="?size=10" class="btn btn-default">10</a> <a href="?size=50"
                                                         class="btn btn-default">50</a> <a href="?size=100"
                                                                                           class="btn btn-default">100</a>
</div>
