<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.project.ProjectActionBean.create" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.project.CreateProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.project.CreateProjectActionBean" class="form-horizontal">
            <fieldset>
                <legend><f:message key="bbmri.action.project.CreateProjectActionBean.fifthStep"/></legend>

                <div class="control-group">
                    <s:label for="name" class="control-label"/>
                    <div class="controls">
                        <s:text id="name" name="project.name" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="fundingOrganization" class="control-label"/>
                    <div class="controls">
                        <s:text id="fundingOrganization" name="project.fundingOrganization" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="approvedBy" class="control-label"/>
                    <div class="controls">
                        <s:text id="approvedBy" name="project.approvedBy" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="approvalStorage" class="control-label"/>
                    <div class="controls">
                        <s:text id="approvalStorage" name="project.approvalStorage" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="principalInvestigator" class="control-label"/>
                    <div class="controls">
                        <s:text id="principalInvestigator" name="project.principalInvestigator" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="homeInstitution" class="control-label"/>
                    <div class="controls">
                        <s:text id="homeInstitution" name="project.homeInstitution" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="annotation" class="control-label"/>
                    <div class="controls">
                        <s:textarea id="annotation" name="project.annotation" readonly="true"/>
                    </div>
                </div>

                <div class="form-actions">
                    <s:submit name="confirmStep5" class="btn btn-primary btnMargin"/>
                    <s:submit name="backFromStep5" class="btn btn-inverse"/>
                </div>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>