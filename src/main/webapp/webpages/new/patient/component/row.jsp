<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${item.institutionalId}</td>
    <td><f:message key="cz.bbmri.entity.Sex.${item.sex.name}"/></td>
    <td>${item.age}</td>
    <td>${item.consent}</td>

</s:layout-definition>

