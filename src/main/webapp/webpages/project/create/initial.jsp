<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="projects.createProject" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.project.CreateProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.project.CreateProjectActionBean" method="GET">
            <fieldset>
                <legend><f:message key="project_upload_new"/></legend>
                <li><f:message key="project_upload_information_1"/></li>
                <li><f:message key="project_upload_information_2"/></li>
                <li><f:message key="project_upload_information_3"/>
                    <s:link href="/data/MTA_BBMRICZ.doc" target="blank"><f:message key="download_mta_form"/></s:link></li>
                <br>
                <br>
            </fieldset>
            <br>
            <s:submit name="general"><f:message key="continue"/></s:submit>
        </s:form>
    </s:layout-component>
</s:layout-render>