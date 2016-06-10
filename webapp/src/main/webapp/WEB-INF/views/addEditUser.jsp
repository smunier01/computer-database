<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mylib2" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:include page="top.jsp"/>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<spring:message code="addUser.addUser" var="addUser"/>
<spring:message code="editUser.editUser" var="editUser"/>
<spring:message code="addUser.username" var="username"/>
<spring:message code="addUser.password" var="password"/>
<spring:message code="addUser.role" var="role"/>
<spring:message code="addUser.add" var="add"/>
<spring:message code="editUser.edit" var="edit"/>
<spring:message code="addUser.formOr" var="formOr"/>
<spring:message code="addUser.cancel" var="cancel"/>

<!-- Edit of Add computer? -->
<c:choose>
	<c:when test="${not empty param.id}">
		<c:set var="title" value="${editUser}"/>
		<c:set var="submit" value="${edit}"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="${addUser}"/>
		<c:set var="submit" value="${add}"/>
	</c:otherwise>
</c:choose>

<body>
<jsp:include page="header.jsp"/>

<section id="main">

    <div class="container">

        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1>${title}</h1>
                <form method="POST">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="id" value="${user.id}"/>
                    <fieldset>
                        <div >
                            <label class="control-label" for="username">${addUser}</label>
                            <input type="text" class="form-control" name="username" id="username" placeholder="${username}" value="${user.username}" required="true">
                        </div>
                        
                        <!-- Specify the password is not necessary, modified only if changed -->
                        <div >
                            <label class="control-label" for="password">${password}</label>
                            <c:choose>
								<c:when test="${not empty param.id}">
                            		<input type="password" class="form-control" name="password" id="password" placeholder="${password}" value="*****"/>
								</c:when>
								<c:otherwise>
                            		<input type="password" class="form-control" name="password" id="password" placeholder="${password}" required="true" />
								</c:otherwise>
							</c:choose>
                        </div>
                        
                        <div >
                            <label class="control-label" for="role">${role}</label>
                            <select class="form-control" name="role" id="role">
                            	<option value="USER">USER</option>
                            	<option value="ADMIN">ADMIN</option>
                        	</select>
                        </div>
                    </fieldset>
                    <br/>
					<!-- Validation button -->
                    <div class="actions pull-right">
                        <input id="addButtonUser" type="submit" value="${submit}" class="btn btn-primary"> ${formOr} <a href="${pageContext.request.contextPath}/admin" class="btn btn-default">${cancel}</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
</body>
</html>
