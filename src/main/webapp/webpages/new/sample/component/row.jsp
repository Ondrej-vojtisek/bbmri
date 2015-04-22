<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <td>${item.institutionalId}</td>
    <td><format:formatDate value="${item.takingDate}" type="both"/></td>
    <td>${item.materialType}</td>
    <td>
        <core:if test="${not empty item.quantity}">
            ${item.quantity}
        </core:if>
    </td>
    <td>${item.diagnosis}</td>
    <td>
        <core:if test="${not empty item.biopticalReport}">
            ${item.biopticalReport}
        </core:if>
    </td>

</stripes:layout-definition>