<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag body-content="empty" %>
<%@ attribute name="current" required="true" %>
<%@ attribute name="count" required="true" %>
<%@ attribute name="psize" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="indexStart" value="0" />

<c:if test="${(current - 5) >= 0}">
	<c:set var="indexStart" value="${current - 5}" />
</c:if>

<c:set var="indexStop" value="${count - 1}" />

<c:if test="${(current + 5) <= (count - 1)}">
	<c:set var="indexStop" value="${current + 5}" />
</c:if>

<c:choose>
	<c:when test="${current == 0}">
		<li class="disabled"><a aria-label="Previous"> <span
				aria-hidden="true">&laquo;</span></a></li>
	</c:when>
	<c:otherwise>
		<li><a href="?page=${current - 1}" aria-label="Previous"> <span
				aria-hidden="true">&laquo;</span></a></li>
	</c:otherwise>
</c:choose>

<c:if test="${indexStart > 0}">
	<li><a href="?page=0">0</a></li>
	<li class="disabled"><a>&hellip;</a></li>
</c:if>

<c:forEach var="i" begin="${indexStart}" end="${indexStop}">
	<li ${current == i ? 'class="active"' : ''}><a href="?page=${i}">${i + 1}</a></li>
</c:forEach>

<c:if test="${indexStop < (count - 1)}">
	<li class="disabled"><a>&hellip;</a></li>
	<li><a href="?page=${count - 1}">${count}</a></li>
</c:if>

<c:choose>
	<c:when test="${current == (count - 1)}">
		<li class="disabled"><a aria-label="Next"> <span
				aria-hidden="true">&laquo;</span></a></li>
	</c:when>
	<c:otherwise>
		<li><a href="?page=${current + 1}" aria-label="Next"> <span
				aria-hidden="true">&laquo;</span></a></li>
	</c:otherwise>
</c:choose>
