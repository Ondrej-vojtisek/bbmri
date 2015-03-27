<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${item.institutionalId}</td>
    <td><s:layout-render name="${component.date}" date="${item.takingDate}"/></td>
    <td>${item.materialType}</td>
    <td>
        <c:if test="${not empty item.quantity}">
            ${item.quantity.available}/${item.biopticalReport.total}
        </c:if>
    </td>
    <td>${item.diagnosis}</td>
    <td>
        <c:if test="${not empty item.biopticalReport}">
            ${item.biopticalReport.year}/${item.biopticalReport.number}
        </c:if>
    </td>

</s:layout-definition>