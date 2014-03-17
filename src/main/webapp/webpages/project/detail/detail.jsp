<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%--<f:message key="cz.bbmri.action.project.ProjectActionBean.detail" var="title"/>--%>
<s:useActionBean var="createSampleQuestionBean" beanclass="cz.bbmri.action.request.CreateSampleQuestion"/>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="detail">

    <s:layout-component name="body">

        <s:form beanclass="${actionBean.name}" class="form-horizontal">

            <c:set var="readonly" value="${true}"/>

            <security:allowed event="update">
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
                <security:allowed event="update">
                    <s:submit name="update" class="btn btn-primary btnMargin">
                        <s:param name="projectId" value="${actionBean.projectId}"/>
                    </s:submit>
                </security:allowed>

                <c:if test="${actionBean.isApproved}">
                    <security:allowed bean="createSampleQuestionBean" event="createSampleRequest">
                        <s:link beanclass="cz.bbmri.action.request.CreateSampleQuestion" event="createSampleRequest"
                                class="btn btn-primary btnMargin">
                            <s:param name="projectId" value="${actionBean.projectId}"/>
                            <f:message key="cz.bbmri.action.project.ProjectActionBean.createSampleRequest"/>
                        </s:link>
                    </security:allowed>
                </c:if>

                <c:if test="${actionBean.isStarted}">
                    <security:allowed event="markAsFinished">
                        <s:submit name="markAsFinished" class="btn btn-primary btnMargin">
                            <s:param name="projectId" value="${actionBean.projectId}"/>
                        </s:submit>
                    </security:allowed>
                </c:if>

                <c:if test="${actionBean.isNew}">

                    <security:allowed event="approve">
                        <s:submit name="approve" class="btn btn-primary btnMargin">
                            <s:param name="projectId" value="${actionBean.projectId}"/>
                        </s:submit>
                    </security:allowed>

                    <security:allowed event="deny">
                        <s:submit name="deny" class="btn btn-danger">
                            <s:param name="projectId" value="${actionBean.projectId}"/>
                        </s:submit>
                    </security:allowed>

                </c:if>

            </div>
        </s:form>

    </s:layout-component>
</s:layout-render>
