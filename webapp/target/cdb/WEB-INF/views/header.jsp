<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard"> Application - Computer Database </a>
		<a class="navbar-brand navbar-right" href="?lang=fr">FR</a>
		<a class="navbar-brand navbar-right" href="?lang=en">EN</a>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Logout"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
	</div>
</header>