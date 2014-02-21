<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${requestGroup.id}</td>
    <td>${requestGroup.lastModification}</td>
    <td>${requestGroup.amountOfSamples}</td>
    <td>${requestGroup.totalAmountOfAliquotes}</td>

</s:layout-definition>

