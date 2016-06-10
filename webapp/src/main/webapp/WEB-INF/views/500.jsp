<%@ page session="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mylib2" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<jsp:include page="top.jsp"/>

<c:set var="context" value="${pageContext.request.contextPath}"/>


<html>
<jsp:include page="top.jsp"/>
<body>
<jsp:include page="header.jsp"/>

	<section id="main">
		<div class="container">	
			<div class="alert alert-danger">
				<spring:message code="i18n.500" />
				<br/>
				<!-- stacktrace -->
			</div>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="text-center">
			<small><spring:message code="dashboard.footer" /> - <a><spring:message code="dashboard.legal" /></a></small>
		</div>
	</footer>

	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/dashboard.js"></script>

</body>
</html>