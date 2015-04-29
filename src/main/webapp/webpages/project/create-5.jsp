<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.action.ProjectCreateActionBean.create" var="title"/>

<stripes:layout-render name="${component.layout.content}" title="${title}"
                       primarymenu="project">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.ProjectCreateActionBean" class="form-horizontal">
            <fieldset>
                <legend><format:message key="cz.bbmri.action.ProjectCreateActionBean.fifthStep"/></legend>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Project.name" class="control-label">
                        <format:message key="cz.bbmri.entity.Project.name"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="project.name" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Project.fundingOrganization" class="control-label">
                        <format:message key="cz.bbmri.entity.Project.fundingOrganization"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="project.fundingOrganization" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Project.approvedBy" class="control-label">
                        <format:message key="cz.bbmri.entity.Project.approvedBy"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="project.approvedBy" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Project.approvalStorage" class="control-label">
                        <format:message key="cz.bbmri.entity.Project.approvalStorage"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="project.approvalStorage" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Project.principalInvestigator" class="control-label">
                        <format:message key="cz.bbmri.entity.Project.principalInvestigator"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="project.principalInvestigator" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Project.homeInstitution" class="control-label">
                        <format:message key="cz.bbmri.entity.Project.homeInstitution"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="project.homeInstitution" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Project.annotation" class="control-label">
                        <format:message key="cz.bbmri.entity.Project.annotation"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:textarea name="project.annotation" readonly="true"/>
                    </div>
                </div>

                <div class="form-actions">
                    <stripes:submit name="confirm" class="btn btn-primary btnMargin"/>
                    <stripes:submit name="backToFourth" class="btn btn-inverse"/>
                </div>
            </fieldset>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>