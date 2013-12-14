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
                <s:label for="name" class="control-label"/>
                <div class="controls">
                    <s:text id="name" name="project.name" readonly="${readonly}"/>
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
                <s:label for="homeInstitution" class="control-label"/>
                <div class="controls">
                    <s:text id="homeInstitution" name="project.homeInstitution" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="projectState" class="control-label"/>
                <div class="controls">
                    <s:text id="projectState" name="project.projectState" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="annotation" class="control-label"/>
                <div class="controls">
                    <s:textarea id="annotation" name="project.annotation" readonly="${readonly}"/>
                </div>
            </div>


            <div class="form-actions">
                <security:allowed bean="projectBean" event="edit">
                    <s:submit name="update" class="btn btn-primary btnMargin">
                        <s:param name="id" value="${projectBean.id}"/>
                    </s:submit>
                </security:allowed>

                <c:if test="${projectBean.isStarted}">
                    <security:allowed bean="projectBean" event="markAsFinished">
                        <s:submit name="markAsFinished" class="btn btn-primary btnMargin">
                            <s:param name="id" value="${projectBean.id}"/>
                        </s:submit>
                    </security:allowed>
                </c:if>

                <c:if test="${projectBean.isNew}">

                    <security:allowed bean="projectBean" event="approve">
                        <s:submit name="approve" class="btn btn-primary btnMargin">
                            <s:param name="id" value="${projectBean.id}"/>
                        </s:submit>
                    </security:allowed>

                    <security:allowed bean="projectBean" event="deny">
                        <s:submit name="deny" class="btn btn-danger">
                            <s:param name="id" value="${projectBean.id}"/>
                        </s:submit>
                    </security:allowed>

                </c:if>

            </div>
        </s:form>

    </s:layout-component>
</s:layout-render>
