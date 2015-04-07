<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <td>${record.name}</td>
    <td>${record.fundingOrganization}</td>
    <td><format:message key="cz.bbmri.entity.enumeration.ProjectState.${record.projectState}"/></td>

</stripes:layout-definition>

