<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="../top.jsp" />

<body>
	<jsp:include page="../header.jsp" />

	<section id="main">
		<div class="container">
			<c:forEach items="${errors.getAllErrors()}" var="error">
				<div>${error}</div>
			</c:forEach>
		</div>
	</section>
</body>
</html>