<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mylib2" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:include page="top.jsp"/>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<spring:message code="form.cancel" var="cancel"/>
<spring:message code="form.or" var="formOr"/>
<spring:message code="edit.computer" var="editComputer"/>
<spring:message code="column.name" var="columnName"/>
<spring:message code="column.introduced" var="columnIntroduced"/>
<spring:message code="column.discontinued" var="columnDiscontinued"/>
<spring:message code="column.company" var="columnCompany"/>

<body>
<jsp:include page="header.jsp"/>

<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <div class="label label-default pull-right">id:
                    ${computer.id}</div>
                <h1>${editComputer}</h1>

                <form action="${contextPath}/computer/edit"
                      method="POST">
                    <input id="id" name="id" type="hidden" value="${computer.id}"/>
                    <fieldset>
                        <div class="form-group ${errors.hasFieldErrors('name') ? 'has-error': ''}">
                            <label for="computerName">${columnName}</label> <input
                                type="text" class="form-control" name="name"
                                id="computerName" placeholder="${columnName}"
                                value="${computer.name}">
                        </div>
                        <div class="form-group ${errors.hasFieldErrors('introduced') ? 'has-error': ''}">
                            <label for="introduced">${columnIntroduced}</label> <input
                                class="form-control" name="introduced"
                                id="introduced" placeholder="${columnIntroduced}"
                                value="${computer.introduced}">
                        </div>
                        <div class="form-group ${errors.hasFieldErrors('discontinued') ? 'has-error': ''}">
                            <label for="discontinued">${columnDiscontinued}</label> <input
                                class="form-control" name="discontinued"
                                id="discontinued" placeholder="${columnDiscontinued}"
                                value="${computer.discontinued}">
                        </div>
                        <div class="form-group ${errors.hasFieldErrors('companyId') ? 'has-error': ''}">
                            <label for="companyId">${columnCompany}</label> <select
                                class="form-control" name="companyId" id="companyId">
                            <option value="">--</option>
                            <c:forEach items="${companies}" var="company">
                                ${computer.companyId}
                                <option value="${company.id}"
                                    ${company.id == computer.companyId ? 'selected' : ''}>${company.name}</option>
                            </c:forEach>
                        </select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input id="buttonForm" type="submit" value="Edit"
                               class="btn btn-primary"> ${formOr} <a href="dashboard.html"
                                                                     class="btn btn-default">${cancel}</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
</body>
</html>