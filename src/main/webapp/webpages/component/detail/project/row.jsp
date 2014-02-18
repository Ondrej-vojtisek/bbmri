<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${project.name}</td>
    <td>${project.fundingOrganization}</td>
    <td><f:message key="ProjectState.${project.projectState}"/></td>

</s:layout-definition>

