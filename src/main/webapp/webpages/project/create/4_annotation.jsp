<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<format:message key="cz.bbmri.action.project.ProjectActionBean.create" var="title"/>
<stripes:useActionBean var="ab" beanclass="cz.bbmri.action.project.CreateProjectActionBean"/>
<stripes:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.project.CreateProjectActionBean">
            <fieldset>
                <legend><format:message key="cz.bbmri.action.project.CreateProjectActionBean.fourthStep"/></legend>

                <stripes:label for="cz.bbmri.entities.Project.annotation"/>
                <stripes:textarea name="project.annotation"/>

                <div class="form-actions">
                    <stripes:submit name="confirmStep4" class="btn btn-primary btnMargin"/>
                    <stripes:submit name="backFromStep4" class="btn btn-inverse"/>
                </div>
            </fieldset>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>