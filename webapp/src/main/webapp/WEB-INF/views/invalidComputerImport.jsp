<%@ page session="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mylib2" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
    </div>

    <form id="deleteForm" action="${context}/computer/delete" method="POST">
        <input type="hidden" name="selection" value="">
    </form>

    <div class="container" style="margin-top: 10px;">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <!-- Variable declarations for passing labels as parameters -->
                <!-- Table header for Computer Name -->


                <th>
                    <mylib2:link target="" name="${columnName}" params="${page.params}" order="name"/>
                </th>
                <th><mylib2:link target="" name="${columnIntroduced}" params="${page.params}"
                                 order="introduced"/></th>
                <!-- Table header for Discontinued Date -->
                <th><mylib2:link target="" name="${columnDiscontinued}" params="${page.params}"
                                 order="discontinued"/></th>
                <!-- Table header for Company -->
                <th><mylib2:link target="" name="${columnCompany}" params="${page.params}"
                                 order="company_name"/></th>

            </tr>
            </thead>
            <!-- Browse attribute computers -->
            <tbody id="results">
            <form action="${contextPath}/import" method="POST">
                <c:forEach items="${page.list}" var="computer">
                    <tr>
                        <td><input type="text" name="name[]" value="${computer.name}"></td>
                        <td><input type="text" name="introduced[]" value="${computer.introduced}"></td>
                        <td><input type="text" name="discontinued[]" value="${computer.discontinued}"></td>
                        <td><input type="text" name="companyName[]" value="${computer.companyName}"></td>
                    </tr>
                </c:forEach>
            </form>
            </tbody>
        </table>
    </div>

    <div class="container">
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <a class="btn btn-default" id="addComputer" href="#">Cancel</a>
            </div>
            <div class="pull-right">
                <a class="btn btn-default" id="addComputer" href="#">Export Errors as CSV</a>
                <a class="btn btn-default" id="addComputer" href="#">Ignore Errors</a>
                <a class="btn btn-success" id="addComputer" href="#">Submit</a>
            </div>
        </div>
    </div>
</section>

</body>
<script type="text/javascript">
    $.springMessages = {
        deleteConfirmation: "${deleteConfirmation}"
    };
</script>
</html>
