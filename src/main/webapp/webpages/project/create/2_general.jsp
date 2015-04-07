<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.action.project.ProjectActionBean.create" var="title"/>
<stripes:useActionBean var="ab" beanclass="cz.bbmri.action.project.CreateProjectActionBean"/>
<stripes:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <stripes:layout-component name="body">


        <fieldset>
            <legend><format:message key="cz.bbmri.action.project.CreateProjectActionBean.secondStep"/></legend>

            <stripes:form beanclass="cz.bbmri.action.project.CreateProjectActionBean" class="form-horizontal">

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Project.name" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="project.name"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Project.principalInvestigator" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="project.principalInvestigator"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Project.homeInstitution" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="project.homeInstitution"/>
                    </div>
                </div>

                <div class="form-actions">
                    <stripes:submit name="confirmStep2" class="btn btn-primary btnMargin"/>

                    <stripes:link beanclass="cz.bbmri.action.project.CreateProjectActionBean" event="initial"
                            class="btn btn-inverse">
                        <format:message key="back"/>
                    </stripes:link>
                </div>
            </stripes:form>

        </fieldset>
    </stripes:layout-component>
</stripes:layout-render>