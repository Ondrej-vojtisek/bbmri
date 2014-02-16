<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${container.name}</td>
    <td>${container.location}</td>
    <td>${container.capacity}</td>
    <td>${container.numberOfRacks}</td>
    <td>${container.tempMin}</td>
    <td>${container.tempMax}</td>

</s:layout-definition>

