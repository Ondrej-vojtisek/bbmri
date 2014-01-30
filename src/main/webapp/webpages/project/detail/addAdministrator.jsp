<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.project.ProjectActionBean.detail" var="title"/>

<s:useActionBean var="adminFindBean" beanclass="cz.bbmri.action.project.FindProjectAdminActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="addAdministrator">

    <s:layout-component name="body">
  
        <s:layout-render name="/webpages/component/findUser.jsp"
                            findBean="${adminFindBean}"
                            context="project"/>
           
    </s:layout-component>
</s:layout-render>