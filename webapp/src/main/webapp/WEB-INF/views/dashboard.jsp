<%@ page session="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mylib2" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<jsp:include page="top.jsp"/>

<c:set var="context" value="${pageContext.request.contextPath}"/>

<spring:message code="column.name" var="columnName"/>
<spring:message code="column.introduced" var="columnIntroduced"/>
<spring:message code="column.discontinued" var="columnDiscontinued"/>
<spring:message code="column.company" var="columnCompany"/>
<spring:message code="dashboard.filterbyname" var="filterByName"/>
<spring:message code="dashboard.filterall" var="filterall"/>
<spring:message code="dashboard.filtercompany" var="filtercompany"/>
<spring:message code="dashboard.filtercomputer" var="filtercomputer"/>
<spring:message code="dashboard.addcomputer" var="addComputer"/>
<spring:message code="dashboard.edit" var="editComputer"/>
<spring:message code="dashboard.found" var="foundComputer"/>
<spring:message code="dashboard.admin" var="admin"/>
<spring:message code="delete.confirmation" var="deleteConfirmation"/>
<spring:message code="dashboard.search" var="search"/>

<body>
<jsp:include page="header.jsp"/>
<section id="main">
    <div class="container">
        <h1 id="homeTitle">
            <span id="nbComputers">${page.totalCount}</span>&nbsp;<span>${foundComputer}</span>
        </h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form id="searchForm" action="#" method="GET" class="form-inline">

                    <input type="search" id="searchbox" name="search" autocomplete="off" class="form-control" placeholder=${search} value="${param.search}"  />
                    <input id="searchType" name="searchType" type="hidden">

                    <div class="btn-group">
                        <button type="submit" id="searchsubmit" class="btn btn-primary raised">${ param.searchType == "" ? filterall : (param.searchType =="computer" ? filtercomputer:filtercompany)}</button>
                        <button type="button" class="btn btn-primary raised dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="caret"></span>
                            <span class="sr-only">Toggle Dropdown</span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a href="#" class="searchoption" onclick="$('#searchType').val(''); $('#searchForm').submit()">${filterall}</a></li>
                            <li><a href="#" class="searchoption" onclick="$('#searchType').val('company'); $('#searchForm').submit()">${filtercompany}</a></li>
                            <li><a href="#" class="searchoption" onclick="$('#searchType').val('computer'); $('#searchForm').submit()">${filtercomputer}</a></li>
                        </ul>
                    </div>
                </form>
            </div>
            <div class="pull-right">
                <c:if test="${isAdmin}">
                	<a class="btn btn-success" id="addComputer" href="${context}/computer/add">${addComputer}</a>
					<a class="btn btn-warning" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">${editComputer}</a>
                	<a class="btn btn-default" id="admin" href="admin">${admin}</a>
                </c:if>
            </div>
        </div>
    </div>

    <form id="deleteForm" action="${context}/computer/delete" method="POST">
        <input type="hidden" name="selection" value="">
    </form>

    <div class="container" style="margin-top: 10px;">
        <table class="table table-hover">
            <thead>
            <tr>
                <!-- Variable declarations for passing labels as parameters -->
                <!-- Table header for Computer Name -->

				<c:if test="${isAdmin}">
                	<th class="editMode" style="width: 60px; height: 22px;">
                		<input type="checkbox" id="selectall"/>
                		<span style="vertical-align: top;"> - <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected('${deleteConfirmation}');">
                		<i class="fa fa-trash-o fa-lg"></i></a>
                        </span>
                    </th>
                </c:if>
                <th>
                	<mylib2:link target="" name="${columnName}" params="${page.params}" order="name"/>
                </th>
                <th class="text-center"><mylib2:link target="" name="${columnIntroduced}" params="${page.params}"
                                 order="introduced"/></th>
                <!-- Table header for Discontinued Date -->
                <th class="text-center"><mylib2:link target="" name="${columnDiscontinued}" params="${page.params}"
                                 order="discontinued"/></th>
                <!-- Table header for Company -->
                <th><mylib2:link target="" name="${columnCompany}" params="${page.params}"
                                 order="company_name"/></th>

            </tr>
            </thead>
            <!-- Browse attribute computers -->
            <tbody id="results">
	            <c:forEach items="${page.list}" var="computer">
	                <tr>
	                    <c:if test="${isAdmin}">
	                    	<td class="editMode">
	                    		<input type="checkbox" name="cb" id="${computer.name}_id" class="cb" value="${computer.id}">
	                    	</td>
	                    </c:if>
	                    <td>
	                    	<c:choose>
		  						<c:when test="${isAdmin}">
		  							<a id="${computer.name}_name" href="${context}/computer/edit?id=${computer.id}" onclick="">${computer.name}</a>
			  					</c:when>
			  					<c:otherwise>
			  						${computer.name}
								</c:otherwise>
							</c:choose>

	                   	</td>
	                    <td class="text-center">${computer.introduced}</td>
	                    <td class="text-center">${computer.discontinued}</td>
	                    <td>${computer.companyName}</td>
	                </tr>
	            </c:forEach>
            </tbody>
        </table>
    </div>
</section>

<footer class="navbar-fixed-bottom">
    <div class="text-center">
        <mylib2:pagination2 current="${page.params.pageNumber}" count="${page.numberOfPages()}"
            psize="${page.params.size}" />
    </div>
    <div class="text-center">
        <small><spring:message code="dashboard.footer" /> - <a><spring:message code="dashboard.legal" /></a></small>
    </div>
</footer>
<script src="${pageContext.request.contextPath}/js/autocomplete.js"></script>
</body>
<script type="text/javascript">
    $.springMessages = {
        deleteConfirmation: "${deleteConfirmation}"
    };

    $(function(){
        $(".searchoption").on("click",function(){
            console.log($(this).text());
            $('#searchsubmit').text($(this).text());
        })
    });
</script>
</html>
