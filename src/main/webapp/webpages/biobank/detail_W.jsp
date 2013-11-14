<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.biobank.BiobankActionBean.detail" var="title"/>
<s:useActionBean var="biobankBean" beanclass="bbmri.action.biobank.BiobankActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="detail">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/biobank/component/detail.jsp" bean="${biobankBean}" editable="true"/>

    </s:layout-component>
</s:layout-render>
