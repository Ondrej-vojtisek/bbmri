<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="projects.createProject" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.project.ProjectActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.wholeName}"
                 primarymenu="project"
                 biobank="${null}"
                 administrator="${ab.loggedUser.administrator}"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.project.ProjectActionBean">
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
            <s:link beanclass="bbmri.action.project.ProjectActionBean" event="createProject" style="float: right;"><f:message key="continue"/></s:link>
        </s:form>
    </s:layout-component>
</s:layout-render>