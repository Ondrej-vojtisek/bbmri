<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%--<format:message key="cz.bbmri.action.project.ProjectActionBean.detail" var="title"/>--%>
<stripes:useActionBean var="createSampleQuestionBean" beanclass="cz.bbmri.action.request.CreateSampleQuestion"/>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="detail">

    <stripes:layout-component name="body">

        <stripes:form beanclass="${actionBean.name}" class="form-horizontal">

            <core:set var="readonly" value="${true}"/>

            <security:allowed event="update">
                <core:set var="readonly" value="${false}"/>
            </security:allowed>

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
                <stripes:label for="cz.bbmri.entities.Project.approvalDate" class="control-label"/>
                <div class="controls">
                    <stripes:text name="project.approvalDate" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Project.projectState" class="control-label"/>
                <div class="controls">
                    <stripes:text name="project.projectState" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Project.annotation" class="control-label"/>
                <div class="controls">
                    <stripes:textarea name="project.annotation" readonly="${readonly}"/>
                </div>
            </div>


            <div class="form-actions">
                <security:allowed event="update">
                    <stripes:submit name="update" class="btn btn-primary btnMargin">
                        <stripes:param name="projectId" value="${actionBean.projectId}"/>
                    </stripes:submit>
                </security:allowed>

                <core:if test="${actionBean.isApproved}">
                    <security:allowed bean="createSampleQuestionBean" event="createSampleRequest">
                        <stripes:link beanclass="cz.bbmri.action.request.CreateSampleQuestion" event="createSampleRequest"
                                class="btn btn-primary btnMargin">
                            <stripes:param name="projectId" value="${actionBean.projectId}"/>
                            <format:message key="cz.bbmri.action.project.ProjectActionBean.createSampleRequest"/>
                        </stripes:link>
                    </security:allowed>
                </core:if>

                <core:if test="${actionBean.isConfirmed}">
                    <security:allowed event="markAsFinished">
                        <stripes:submit name="markAsFinished" class="btn btn-primary btnMargin">
                            <stripes:param name="projectId" value="${actionBean.projectId}"/>
                        </stripes:submit>
                    </security:allowed>
                </core:if>

                <core:if test="${actionBean.isNew}">

                    <security:allowed event="approve">
                        <stripes:submit name="approve" class="btn btn-primary btnMargin">
                            <stripes:param name="projectId" value="${actionBean.projectId}"/>
                        </stripes:submit>
                    </security:allowed>

                    <security:allowed event="deny">
                        <stripes:submit name="deny" class="btn btn-danger">
                            <stripes:param name="projectId" value="${actionBean.projectId}"/>
                        </stripes:submit>
                    </security:allowed>

                </core:if>

            </div>
        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>
