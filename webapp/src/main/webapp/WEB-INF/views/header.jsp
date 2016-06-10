<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="header.hello" var="hello"/>

<header class="navbar navbar-default navbar-fixed-top">
    <ul class="nav navbar-nav navbar-left">
        <li><a href="${pageContext.request.contextPath}/dashboard" class="navbar-brand navbarlink"> <img src="${pageContext.request.pathTranslated}/css/images/logo.png" alt="logo" class="logo" /> Application - Computer Database</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
        <li class="dropdown"><a class="dropdown-toggle navbarlink"
                                data-toggle="dropdown" href="#" role="button" aria-haspopup="true"
                                aria-expanded="false">

                <spring:message code="i18n.lang" text="Language" />
            <span class="caret"></span>
        </a>
            <ul class="dropdown-menu" style="z-index: 99999">
                <li><a
                        href="${pageContext.request.pathTranslated }?lang=en"><img alt="en"
                        src="${pageContext.request.contextPath}/css/images/en.png"
                        class="flag" alt="English" /> English</a></li>
                <li><a
                        href="${pageContext.request.pathTranslated }?lang=fr"><img alt="fr"
                        src="${pageContext.request.contextPath}/css/images/fr.png"
                        class="flag" alt="France" /> Français</a></li>
                <li role="separator" class="divider"></li>
                <li class="text-center"><spring:message code="i18n.current" />:
                    ${pageContext.response.locale}</li>
            </ul></li>

        <c:choose>
            <c:when test="${ not empty pageContext.request.userPrincipal.name }">
                <li class="dropdown">
                    <a class="dropdown-toggle navbarlink"
                       data-toggle="dropdown" href="#" role="button" aria-haspopup="true"
                       aria-expanded="false">
                        ${hello}
                        <c:out
                                value="${pageContext.request.userPrincipal.name}" /> <span
                            class="caret"></span></a>
                    <ul class="dropdown-menu" style="z-index: 99999">
                        <li>
                            <spring:message code="button_logout" var="logoutmsg" />
                            <c:url var="logoutUrl" value="/logout" />
                            <form class="form-inline" action="${logoutUrl}" method="post">
                                <input type="submit" class="btn btn-link"
                                       value="${ logoutmsg }" />
                            </form>
                        </li>
                    </ul>
                </li>
            </c:when>
            <c:otherwise>
                <li><a href="${ pageContext.request.contextPath }/login" class="navbarlink"><spring:message
                        code="button_login" /></a></li>
            </c:otherwise>
        </c:choose>

    </ul>
</header>