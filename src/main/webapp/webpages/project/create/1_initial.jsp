<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.action.project.ProjectActionBean.create" var="title"/>
<stripes:useActionBean var="ab" beanclass="cz.bbmri.action.project.CreateProjectActionBean"/>
<stripes:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <stripes:layout-component name="body">
        <stripes:form beanclass="cz.bbmri.action.project.CreateProjectActionBean">
            <fieldset>
                <legend><format:message key="cz.bbmri.action.project.CreateProjectActionBean.firstStep"/></legend>
                <li><format:message key="project_upload_information_1"/></li>
                <li><format:message key="project_upload_information_2"/></li>
                <li><format:message key="project_upload_information_3"/></li>
                <br>
            </fieldset>
            <br>

            <div class="form-actions">
                <stripes:link href="/data/MTA_BBMRICZ.doc" target="blank" class="btn btn-info btnMargin">
                    <format:message key="download_mta_form"/>
                </stripes:link>
                <stripes:submit name="confirmStep1" class="btn btn-primary"/>
            </div>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>