<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${sampleRequest.created}</td>
    <td>${sampleRequest.biobank.name}</td>
    <td><f:message key="cz.bbmri.entities.enumeration.RequestState.${sampleRequest.requestState}"/></td>
    <td>${sampleRequest.specification}</td>

</s:layout-definition>

