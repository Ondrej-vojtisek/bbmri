<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.biobank.BiobankActionBean.detail" var="title"/>

<s:useActionBean var="adminFindBean" beanclass="bbmri.action.biobank.FindBiobankAdminActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="addAdministrator">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/component/findUser.jsp"
                         findBean="${adminFindBean}"
                         context="biobank"/>

    </s:layout-component>
</s:layout-render>
