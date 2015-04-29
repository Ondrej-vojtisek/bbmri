<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <td>${item.id}</td>
    <td><format:formatDate value="${item.created}" type="both"/></td>
    <td>${item.biobank.acronym}</td>

</stripes:layout-definition>

