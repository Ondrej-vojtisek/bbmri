<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${record.name}</td>
    <td>${record.fundingOrganization}</td>
    <td><f:message key="ProjectState.${record.projectState}"/></td>

</s:layout-definition>

