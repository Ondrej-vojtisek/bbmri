<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${request.sample.sampleIdentificator.sampleId}</td>
    <td>${request.sample.materialType.type}</td>
    <td>${request.numOfRequested}</td>

</s:layout-definition>