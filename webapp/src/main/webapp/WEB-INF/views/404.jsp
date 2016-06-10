<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="top.jsp"/>
<body>
<jsp:include page="header.jsp"/>

    <section id="main">
        <div class="container">
            <div class="errortext">
	            <spring:message code="i18n.404" />
                <img class="center-block" src="${pageContext.request.contextPath}/css/images/404.png">
                <br/>
                <!-- stacktrace -->
            </div>
        </div>
    </section>

    <footer>
        <div class="container text-center">
            <p>&nbsp;</p>
            <p>Developed by Excilys Avril 2016</p>
            <p><a>Mentions Légales</a></p>
        </div>
    </footer>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>