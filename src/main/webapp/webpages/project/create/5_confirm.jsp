<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.action.project.ProjectActionBean.create" var="title"/>
<stripes:useActionBean var="ab" beanclass="cz.bbmri.action.project.CreateProjectActionBean"/>
<stripes:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.project.CreateProjectActionBean" class="form-horizontal">
            <fieldset>
                <legend><format:message key="cz.bbmri.action.project.CreateProjectActionBean.fifthStep"/></legend>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Project.name" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="project.name" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Project.fundingOrganization" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="project.fundingOrganization" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Project.approvedBy" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="project.approvedBy" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Project.approvalStorage" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="project.approvalStorage" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Project.principalInvestigator" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="project.principalInvestigator" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Project.homeInstitution" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="project.homeInstitution" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Project.annotation" class="control-label"/>
                    <div class="controls">
                        <stripes:textarea name="project.annotation" readonly="true"/>
                    </div>
                </div>

                <div class="form-actions">
                    <stripes:submit name="confirmStep5" class="btn btn-primary btnMargin"/>
                    <stripes:submit name="backFromStep5" class="btn btn-inverse"/>
                </div>
            </fieldset>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>