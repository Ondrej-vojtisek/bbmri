<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="bbmri.action.project.ProjectActionBean.create" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.project.CreateProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.project.CreateProjectActionBean" method="GET">
            <s:submit name="financed" class="btn btn-inverse"/>
        </s:form>

        <s:form beanclass="bbmri.action.project.CreateProjectActionBean">
            <fieldset>
                <legend><f:message key="bbmri.action.project.CreateProjectActionBean.fourthStep"/></legend>

                <s:label for="z7" name="project.annotation"/>
                <s:textarea id="z7" name="project.annotation"></s:textarea>

                <s:submit name="annotationConfirm" class="btn btn-primary"/>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>