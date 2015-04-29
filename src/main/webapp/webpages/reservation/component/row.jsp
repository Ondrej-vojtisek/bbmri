<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <td><format:formatDate value="${item.created}" type="both"/></td>
    <td>${item.biobank.acronym}</td>
    <td><format:message key="cz.bbmri.entity.ReservationState.${item.reservationState.name}"/></td>
    <td><format:formatDate value="${item.validation}" type="both"/></td>

</stripes:layout-definition>