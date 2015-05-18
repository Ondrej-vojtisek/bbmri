<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <td>${item.id}</td>
    <td>${item.wholeName}</td>
    <td>
        <core:if test="${item.administrator}">
            <format:message key="cz.bbmri.entity.Role.admin"/>
            &nbsp;
        </core:if>
        <core:if test="${item.developer}">
            <format:message key="cz.bbmri.entity.Role.developer"/>
            &nbsp;
        </core:if>
        <core:if test="${item.notAuthorized}">
            <b><format:message key="cz.bbmri.entity.Role.notAuthorized"/></b>
        </core:if>

    </td>

</stripes:layout-definition>
