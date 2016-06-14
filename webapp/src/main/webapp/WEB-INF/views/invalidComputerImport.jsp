<%@ page session="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mylib2" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<jsp:include page="top.jsp"/>

<c:set var="context" value="${pageContext.request.contextPath}"/>

<spring:message code="column.name" var="columnName"/>
<spring:message code="column.introduced" var="columnIntroduced"/>
<spring:message code="column.discontinued" var="columnDiscontinued"/>
<spring:message code="column.company" var="columnCompany"/>
<spring:message code="dashboard.filterbyname" var="filterByName"/>
<spring:message code="dashboard.filtercompany" var="filtercompany"/>
<spring:message code="dashboard.filtercomputer" var="filtercomputer"/>
<spring:message code="dashboard.addcomputer" var="addComputer"/>
<spring:message code="dashboard.edit" var="editComputer"/>
<spring:message code="dashboard.found" var="foundComputer"/>
<spring:message code="delete.confirmation" var="deleteConfirmation"/>

<body>
<jsp:include page="header.jsp"/>
<section id="main">
    <div class="container">
        <h1 id="homeTitle">
            <span id="nbComputers">${page.totalCount}</span>&nbsp<span>${foundComputer}</span>
        </h1>
        <p>
            <c:out value="${nbComputer}"/>/<c:out value="${total}"/> are invalid in yout CSV file...
        </p>
    </div>

    <div class="container">
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <a class="btn btn-default" id="addComputer" href="#">Cancel</a>
            </div>
            <div class="pull-right">
                <a class="btn btn-success" href="#">Export Errors as CSV</a>
                <a class="btn btn-success" href="#">Ignore Errors</a>
            </div>
        </div>
    </div>


</section>

</body>

<script type="text/javascript">
    $.springMessages = {
        deleteConfirmation: "${deleteConfirmation}"
    };

    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });

    $(function () {

        $(".form").on("submit", function (e) {
            e.preventDefault();
            var frm = $(this);
            var data = JSON.stringify(frm.serializeArray());
            console.log(data);
        });

    });

</script>


</html>
