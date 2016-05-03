<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mylib2"%>

<jsp:include page="top.jsp" />

<body>
	<jsp:include page="header.jsp" />

	<section id="main">

		<div class="container">

			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form action="${pageContext.request.contextPath}/computer/add"
						method="POST">
						<fieldset>
							<div class="form-group">
								<label class="control-label" for="computerName">Computer
									name</label> <input type="text" class="form-control"
									name="computerName" id="computerName"
									placeholder="Computer name" value="${computerName}">
							</div>
							<div class="form-group">
								<label class="control-label" for="introduced">Introduced
									date</label> <input type="date" class="form-control" name="introduced"
									id="introduced" placeholder="Introduced date"
									value="${introduced}">
							</div>
							<div class="form-group">
								<label class="control-label" for="discontinued">Discontinued
									date</label> <input type="date" class="form-control"
									name="discontinued" id="discontinued"
									placeholder="Discontinued date" value="${computerName}">
							</div>
							<div class="form-group">
								<label class="control-label" for="companyId">Company</label> <select
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
								class="btn btn-primary"> or <a href="dashboard.html"
								class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/addComputer.js"></script>
</body>
</html>
