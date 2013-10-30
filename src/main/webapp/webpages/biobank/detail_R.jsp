<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="biobank.detail" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.biobank.BiobankActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="detail">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/biobank/component/detail.jsp" editable="false"/>

    </s:layout-component>
</s:layout-render>
