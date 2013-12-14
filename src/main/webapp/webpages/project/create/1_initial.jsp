<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.project.ProjectActionBean.create" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.project.CreateProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.project.CreateProjectActionBean">
            <fieldset>
                <legend><f:message key="bbmri.action.project.CreateProjectActionBean.firstStep"/></legend>
                <li><f:message key="project_upload_information_1"/></li>
                <li><f:message key="project_upload_information_2"/></li>
                <li><f:message key="project_upload_information_3"/></li>
                <br>
            </fieldset>
            <br>

            <div class="form-actions">
                <s:link href="/data/MTA_BBMRICZ.doc" target="blank" class="btn btn-info btnMargin">
                    <f:message key="download_mta_form"/>
                </s:link>
                <s:submit name="confirmStep1" class="btn btn-primary"/>
            </div>
        </s:form>
    </s:layout-component>
</s:layout-render>