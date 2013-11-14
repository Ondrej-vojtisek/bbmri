<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.project.ProjectActionBean.detail" var="title"/>
<s:useActionBean var="projectBean" beanclass="bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="detail">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/project/detail/component/detail.jsp" bean="${projectBean}" editable="false"/>

    </s:layout-component>
</s:layout-render>
