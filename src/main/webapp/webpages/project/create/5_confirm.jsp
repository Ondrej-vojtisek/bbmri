<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.project.ProjectActionBean.create" var="title"/>
<s:useActionBean var="ab" beanclass="cz.bbmri.action.project.CreateProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.project.CreateProjectActionBean" class="form-horizontal">
            <fieldset>
                <legend><f:message key="cz.bbmri.action.project.CreateProjectActionBean.fifthStep"/></legend>

                <div class="control-group">
                    <s:label for="name" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.name" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="fundingOrganization" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.fundingOrganization" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="approvedBy" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.approvedBy" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="approvalStorage" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.approvalStorage" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="principalInvestigator" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.principalInvestigator" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="homeInstitution" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.homeInstitution" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="annotation" class="control-label"/>
                    <div class="controls">
                        <s:textarea name="project.annotation" readonly="true"/>
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