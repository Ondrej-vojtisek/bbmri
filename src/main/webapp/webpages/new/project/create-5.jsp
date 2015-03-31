<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
                 <%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

                 <f:message key="cz.bbmri.action.ProjectCreateActionBean.create" var="title"/>

                 <s:layout-render name="${component.layout.content}" title="${title}"
                                  primarymenu="project">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.ProjectCreateActionBean" class="form-horizontal">
            <fieldset>
                <legend><f:message key="cz.bbmri.action.ProjectCreateActionBean.fifthStep"/></legend>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Project.name" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.name" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Project.fundingOrganization" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.fundingOrganization" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Project.approvedBy" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.approvedBy" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Project.approvalStorage" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.approvalStorage" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Project.principalInvestigator" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.principalInvestigator" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Project.homeInstitution" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.homeInstitution" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Project.annotation" class="control-label"/>
                    <div class="controls">
                        <s:textarea name="project.annotation" readonly="true"/>
                    </div>
                </div>

                <div class="form-actions">
                    <s:submit name="confirm" class="btn btn-primary btnMargin"/>
                    <s:submit name="backToFourth" class="btn btn-inverse"/>
                </div>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>