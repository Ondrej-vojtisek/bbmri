<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.request.RequestActionBean.title" var="title"/>
<s:useActionBean var="requestBean" beanclass="cz.bbmri.action.request.RequestActionBean"/>
<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="">

    <s:layout-component name="body">
        <fieldset>
            <legend></legend>
            <s:form beanclass="${requestBean.name}" class="form-horizontal">

                <div class="control-group">
                    <s:label for="cz.bbmri.entities.SampleQuestion.biobank" class="control-label"/>
                    <div class="controls">
                        <s:text name="sampleQuestion.biobank.name" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entities.SampleQuestion.created" class="control-label"/>
                    <div class="controls">
                        <s:text name="sampleQuestion.created" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entities.SampleQuestion.requestState" class="control-label"/>
                    <div class="controls">
                        <s:text name="sampleQuestion.requestState" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entities.SampleQuestion.specification" class="control-label"/>
                    <div class="controls">
                        <s:textarea name="sampleQuestion.specification" readonly="true"/>
                    </div>
                </div>

                <div class="form-actions">
                    <s:link beanclass="${projectBean.name}" event="detail" class="btn btn-inverse btnMargin">
                        <s:param name="id" value="${requestBean.sampleQuestion.project.id}"/>
                        <f:message key="cz.bbmri.action.request.RequestActionBean.returnToProject"/>
                    </s:link>

                    <security:allowed bean="requestBean" event="approve">
                        <s:submit name="approve" class="btn btn-primary btnMargin">
                            <s:param name="sampleQuestionId" value="${requestBean.sampleQuestionId}"/>
                        </s:submit>
                    </security:allowed>

                    <security:allowed bean="requestBean" event="deny">
                        <s:submit name="deny" class="btn btn-primary btnMargin">
                            <s:param name="sampleQuestionId" value="${requestBean.sampleQuestionId}"/>
                        </s:submit>
                    </security:allowed>

                    <c:if test="${requestBean.isNew}">
                        <security:allowed bean="requestBean" event="delete">
                            <s:submit name="deny" class="btn btn-danger btnMargin">
                                <s:param name="sampleQuestionId" value="${requestBean.sampleQuestionId}"/>
                            </s:submit>
                        </security:allowed>
                    </c:if>
                </div>

            </s:form>


        </fieldset>

        <c:if test="${requestBean.isApproved}">
            <fieldset>
                <legend>

                </legend>

                Is approved, here choose appropriate sample set
            </fieldset>
        </c:if>


    </s:layout-component>
</s:layout-render>
