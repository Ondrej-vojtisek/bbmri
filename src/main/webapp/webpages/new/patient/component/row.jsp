<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <td>${item.institutionalId}</td>
    <td><format:message key="cz.bbmri.entity.Sex.${item.sex.name}"/></td>
    <td>${item.age}</td>
    <td>${item.consent}</td>

</stripes:layout-definition>

