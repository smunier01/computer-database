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

<spring:message code="add.computer" var="addComputer"/>

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
                <h1>${addComputer}</h1>
                <form action="${contextPath}/computer/add" method="POST">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <fieldset>
                        <div
                                class="form-group ${fn:contains(errors, 'name') ? 'has-error': ''}">
                            <label class="control-label" for="name">${columnName}</label> <input type="text"
                                                                                                 class="form-control"
                                                                                                 name="name"
                                                                                                 id="name"
                                                                                                 placeholder="${columnName}"
                                                                                                 value="${computer.name}">
                        </div>
                        <div
                                class="form-group ${fn:contains(errors, 'introduced') ? 'has-error': ''}">
                            <label class="control-label" for="introduced">${columnIntroduced}</label> <input type="date"
                                                                                                             class="form-control"
                                                                                                             name="introduced"
                                                                                                             id="introduced"
                                                                                                             placeholder="${columnIntroduced}"
                                                                                                             value="${computer.introduced}">
                        </div>
                        <div
                                class="form-group ${fn:contains(errors, 'discontinued') ? 'has-error': ''}">
                            <label class="control-label" for="discontinued">${columnDiscontinued}</label> <input
                                type="date" class="form-control"
                                name="discontinued" id="discontinued"
                                placeholder="${columnDiscontinued}"
                                value="${computer.discontinued}">
                        </div>
                        <div
                                class="form-group ${fn:contains(errors, 'companyId') ? 'has-error': ''}">
                            <label class="control-label" for="companyId">${columnCompany}</label> <select
                                class="form-control" name="companyId" id="companyId">
                            <option value="">--</option>
                            <c:forEach items="${companies}" var="company">
                                <option value="${company.id}">${company.name}</option>
                            </c:forEach>
                        </select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input id="buttonForm" type="submit" value="Add"
                               class="btn btn-primary"> ${formOr} <a href="${pageContext.request.contextPath}/dashboard"
                                                                     class="btn btn-default">${cancel}</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
</body>
</html>
