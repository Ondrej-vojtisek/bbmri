<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${record.sample.sampleIdentification.sampleId}</td>
    <td>${record.sample.materialType.type}</td>
    <td>${record.numOfRequested}</td>

</s:layout-definition>