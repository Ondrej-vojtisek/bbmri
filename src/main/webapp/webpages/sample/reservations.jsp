<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.sample.SampleActionBean.detail" var="title"/>
<s:useActionBean var="sampleBean" beanclass="cz.bbmri.action.sample.SampleActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="sample"
                 ternarymenu="reservations">

    <s:layout-component name="body">

        RESERVATIONS

    </s:layout-component>
</s:layout-render>