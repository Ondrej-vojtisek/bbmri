<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${item.name}</td>
    <td>${item.fundingOrganization}</td>
    <td><f:message key="cz.bbmri.entity.ProjectState.${item.projectState}"/></td>

</s:layout-definition>

