<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${record.name}</td>
    <td>${record.capacity}</td>
    <td>${record.numberOfPositions}</td>
    <td>${record.tempMin}</td>
    <td>${record.tempMax}</td>

</s:layout-definition>

