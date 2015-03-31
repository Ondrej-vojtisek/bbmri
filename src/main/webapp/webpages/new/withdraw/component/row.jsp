<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${item.id}</td>
    <td><s:layout-render name="${component.date}" date="${item.created}"/></td>
    <td>${item.biobank.acronym}</td>

</s:layout-definition>

