<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag body-content="empty" dynamic-attributes="dynattrs"%>
<%@ attribute name="target" required="true"%>
<%@ attribute name="name" required="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach items="${dynattrs}" var="a">
	<c:if test="${!empty a.value}">
		<c:set var="myVar" value="${myVar}${a.key}=${a.value}&" />
	</c:if>
</c:forEach>

<a href="${target}?${myVar}">${name}</a>