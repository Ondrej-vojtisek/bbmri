<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:errors/>
<table>
    <tr>
        <th><s:label name="project.id"/></th>
        <th><s:label name="project.name"/></th>
        <th><s:label name="project.annotation"/></th>
        <th><s:label name="project.projectState"/></th>
    </tr>

    <c:forEach items="${ab.projects}" var="project">
        <tr>
            <td><c:out value="${project.id}"/></td>
            <td><c:out value="${project.name}"/></td>
            <td><c:out value="${project.annotation}"/></td>
            <td><f:message key="ProjectState.${project.projectState}"/></td>
        </tr>
    </c:forEach>
</table>


