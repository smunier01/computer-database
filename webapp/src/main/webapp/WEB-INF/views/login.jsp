<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>

<html>
	<jsp:include page="top.jsp"/>
	<body>
		<jsp:include page="header.jsp"/>
		
		<div class="container">
			<form id="login_form" action="${pageContext.request.contextPath}/login" method="post">
				<c:if test="${param.error != null}">
					<div class="alert alert-danger">Invalid username and/or password.</div>
				</c:if>
				<c:if test="${param.logout != null}">
					<div class="alert alert-success">You have been logged out.</div>
				</c:if>
				<p>
					<label for="username"><spring:message code="field_username" /></label>
					<input class="form-control" type="text" id="username" name="username" />
				</p>
				<p>
					<label for="password"><spring:message code="field_password" /></label>
					<input class="form-control" type="password" id="password"
						name="password" />
				</p>
				<input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}"
					value="${_csrf.token}" class="form-control" />
				<button type="submit" name="submit" class="btn btn-primary pull-right raised login">
					<spring:message code="button_login" />
				</button>
			</form>
		</div>
		<footer>
			<div class="container text-center">
				<p>&nbsp;</p>
				<p>Developed by Excilys Avril 2016</p>
				<p><a>Mentions Légales</a></p>
			</div>
		</footer>
		<script	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script	src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<script	src="${pageContext.request.contextPath}/js/dashboard.js"></script>
	</body>
</html>