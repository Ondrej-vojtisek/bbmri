<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <td>${item.institutionalId}</td>
    <td><format:formatDate value="${item.takingDateTime}" type="both"/></td>
    <td><core:if test="${not empty item.materialType}">
        ${item.materialType.name}
        </core:if>
    </td>
    <td>
        <core:if test="${not empty item.quantity}">
            ${item.quantity}
        </core:if>
    </td>
    <td>${item.diagnosisPrint}</td>
    <td>
        <core:if test="${not empty item.biopticalReport}">
            ${item.biopticalReport}
        </core:if>
    </td>

</stripes:layout-definition>