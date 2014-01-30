<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.project.ProjectActionBean.detail" var="title"/>
<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="detail">

    <s:layout-component name="body">

        <s:form beanclass="${projectBean.name}" class="form-horizontal">

            <c:set var="readonly" value="${true}"/>

            <security:allowed bean="projectBean" event="edit">
                <c:set var="readonly" value="${false}"/>
            </security:allowed>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Project.name" class="control-label"/>
                <div class="controls">
                    <s:text name="project.name" readonly="${readonly}"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Project.fundingOrganization" class="control-label"/>
                <div class="controls">
                    <s:text name="project.fundingOrganization" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Project.approvedBy" class="control-label"/>
                <div class="controls">
                    <s:text name="project.approvedBy" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Project.approvalStorage" class="control-label"/>
                <div class="controls">
                    <s:text name="project.approvalStorage" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Project.principalInvestigator" class="control-label"/>
                <div class="controls">
                    <s:text name="project.principalInvestigator" readonly="${readonly}"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Project.homeInstitution" class="control-label"/>
                <div class="controls">
                    <s:text name="project.homeInstitution" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Project.approvalDate" class="control-label"/>
                <div class="controls">
                    <s:text name="project.approvalDate" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Project.projectState" class="control-label"/>
                <div class="controls">
                    <s:text name="project.projectState" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Project.annotation" class="control-label"/>
                <div class="controls">
                    <s:textarea name="project.annotation" readonly="${readonly}"/>
                </div>
            </div>


            <div class="form-actions">
                <security:allowed bean="projectBean" event="edit">
                    <s:submit name="update" class="btn btn-primary btnMargin">
                        <s:param name="projectId" value="${projectBean.projectId}"/>
                    </s:submit>
                </security:allowed>

                <c:if test="${projectBean.isApproved}">
                    <security:allowed bean="projectBean" event="createSampleRequest">
                        <s:link beanclass="${projectBean.name}" event="createSampleRequest" class="btn btn-primary btnMargin">
                            <s:param name="projectId" value="${projectBean.projectId}"/>
                            <f:message key="cz.bbmri.action.project.ProjectActionBean.createSampleRequest"/>
                        </s:link>
                    </security:allowed>
                </c:if>

                <c:if test="${projectBean.isStarted}">
                    <security:allowed bean="projectBean" event="markAsFinished">
                        <s:submit name="markAsFinished" class="btn btn-primary btnMargin">
                            <s:param name="projectId" value="${projectBean.projectId}"/>
                        </s:submit>
                    </security:allowed>
                </c:if>

                <c:if test="${projectBean.isNew}">

                    <security:allowed bean="projectBean" event="approve">
                        <s:submit name="approve" class="btn btn-primary btnMargin">
                            <s:param name="projectId" value="${projectBean.projectId}"/>
                        </s:submit>
                    </security:allowed>

                    <security:allowed bean="projectBean" event="deny">
                        <s:submit name="deny" class="btn btn-danger">
                            <s:param name="projectId" value="${projectBean.projectId}"/>
                        </s:submit>
                    </security:allowed>

                </c:if>

            </div>
        </s:form>

    </s:layout-component>
</s:layout-render>
