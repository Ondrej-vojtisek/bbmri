<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="projects.createProject" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.project.CreateProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">
        <s:errors/>
        <s:form beanclass="bbmri.action.project.CreateProjectActionBean" method="GET">
        <s:submit name="financed">
            <f:message key="back"/>
        </s:submit>
        </s:form>

        <s:form beanclass="bbmri.action.project.CreateProjectActionBean">
            <fieldset>
                <legend><f:message key="project_upload_new"/> - <f:message key="first_step"/></legend>

                <s:label for="z7" name="project.annotation"/>
                <s:textarea id="z7" name="project.annotation"></s:textarea>

                <s:submit name="annotationConfirm"><f:message key="confirm"/></s:submit>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>