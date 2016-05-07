<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mylib2"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="top.jsp" />

<body>
	<jsp:include page="header.jsp" />

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:
						${computer.id}</div>
					<h1>Edit Computer</h1>

					<form action="${pageContext.request.contextPath}/computer/edit"
						method="POST">
						<input id="id" name="id" type="hidden" value="${computer.id}" />
						<fieldset>
							<div class="form-group ${fn:contains(errors, 'name') ? 'has-error': ''}">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" name="computerName"
									id="computerName" placeholder="Computer name"
									value="${computer.name}">
							</div>
							<div class="form-group ${fn:contains(errors, 'introduced') ? 'has-error': ''}">
								<label for="introduced">Introduced date</label> <input
									 class="form-control" name="introduced"
									id="introduced" placeholder="Introduced date"
									value="${computer.introduced}">
							</div>
							<div class="form-group ${fn:contains(errors, 'discontinued') ? 'has-error': ''}">
								<label for="discontinued">Discontinued date</label> <input
									 class="form-control" name="discontinued"
									id="discontinued" placeholder="Discontinued date"
									value="${computer.discontinued}">
							</div>
							<div class="form-group ${fn:contains(errors, 'companyId') ? 'has-error': ''}">
								<label for="companyId">Company</label> <select
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
								class="btn btn-primary"> or <a href="dashboard.html"
								class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>