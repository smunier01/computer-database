<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="params" required="true"
	type="com.excilys.cdb.model.PageParameters"%>
<%@ attribute name="order" required="false"%>
<%@ attribute name="target" required="false"%>
<%@ attribute name="name" required="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:if test="${!empty params.pageNumber}">
	<c:set var="myVar" value="${myVar}page=${params.pageNumber}&" />
</c:if>

<c:if test="${!empty params.size}">
	<c:set var="myVar" value="${myVar}psize=${params.size}&" />
</c:if>

<c:if test="${!empty params.search}">
	<c:set var="myVar" value="${myVar}search=${params.search}&" />
</c:if>

<c:if test="${!empty order}">
	<c:set var="myVar" value="${myVar}order=${order}&" />
	<c:set var="myVar"
		value="${myVar}dir=${param.dir == 'asc' && param.order == order ? 'desc' : 'asc'}" />
</c:if>

<a href="${target}?${myVar}">${name}</a>