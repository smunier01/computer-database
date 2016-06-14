<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ attribute name="errors" required="true"
              type="java.util.Map<com.excilys.core.conflict.format.Fields, java.util.List<com.excilys.core.conflict.format.ErrorMessage>>" %>
<c:forEach var="e" items="${errors}">
    <c:out value="${e.key}"/>
    <c:forEach var="message" items="${e.value}">
        <c:out value="${message}"/>
    </c:forEach>
</c:forEach>