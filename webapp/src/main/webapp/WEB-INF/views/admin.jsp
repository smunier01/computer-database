<%@ page session="false" language="java"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mylib2" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>

<jsp:include page="top.jsp"/>

<c:set var="context" value="${pageContext.request.contextPath}"/>

<spring:message code="admin.admin" var="admin"/>
<spring:message code="admin.addUser" var="addUser"/>
<spring:message code="admin.userName" var="userName"/>
<spring:message code="admin.userRole" var="userRole"/>
<spring:message code="admin.delete" var="delete"/>
<spring:message code="admin.deleteConfirmation" var="deleteConfirmation"/>

<body>
	<jsp:include page="header.jsp" />

	<section id="main">


		<div class="container">
			<h1 id="homeTitle">${admin}</h1>

      <div class="row">
            <div class="col-md-4">
                <div class="well">
                    <form action="import/computers" enctype="multipart/form-data" method="POST">
                        <input type="file" accept="text/xml" id="file" name="file"/>
                        <br/>
                        <input type="submit" value="Add computers" class="btn btn-primary">
                    </form>
                </div>
            </div>
      </div>

        <div id="actions" class="form-horizontal">
            <div class="pull-right">
                <c:if test="${isAdmin}">
                    <a class="btn btn-success" id="addUser" href="${context}/user/addEdit">${addUser}</a>
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">${delete}</a>
                </c:if>
            </div>
        </div>
    </div>

    <form id="deleteForm" action="${context}/user/delete" method="POST">
        <input type="hidden" name="selection" value="">
    </form>

    <div class="container" style="margin-top: 10px;">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <c:if test="${isAdmin}">
                    <th class="editMode" style="width: 60px; height: 22px;">
                        <input type="checkbox" id="selectall"/> <span style="vertical-align: top;"> -
								<a href="#" id="deleteSelected" onclick="$.fn.deleteSelected('${deleteConfirmation}');">
									<i class="fa fa-trash-o fa-lg"></i>
								</a>
							</span></th>

						</c:if>
						<th>${userName}</th>
						<th>${userRole}</th>
					</tr>
				</thead>

				<!-- Browse attribute user -->
				<tbody id="results">
					<c:forEach items="${list}" var="user">
						<tr>
							<c:if test="${isAdmin}">
								<td class="editMode">
									<input type="checkbox" name="cb" id="${user.id}" class="cb" value="${user.id}">
								</td>
							</c:if>
							<td>
								<c:choose>
									<c:when test="${isAdmin}">
										<!-- 	We pass the username to userusername to prevent a strang comportement where Spring concatenate the username form
												et username from the GET request -->
										<a id="${user.username}" href="${context}/user/addEdit?id=${user.id}" onclick="">${user.username}</a>
									</c:when>
									<c:otherwise>${user.username}</c:otherwise>
								</c:choose></td>
							<td>${user.role}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>
</body>

<script type="text/javascript">
    $.springMessages = {
        deleteConfirmation: "${deleteConfirmation}"
    };
</script>
</html>
