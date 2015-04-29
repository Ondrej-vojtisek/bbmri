<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <td>${item.name}</td>
    <td>${item.fundingOrganization}</td>
    <td><format:message key="cz.bbmri.entity.ProjectState.${item.projectState}"/></td>

</stripes:layout-definition>

