<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="createSampleQuestionBean" beanclass="cz.bbmri.action.request.CreateSampleQuestion"/>
<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="">

    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="cz.bbmri.action.request.CreateSampleQuestion.createSampleRequest"/></legend>

            <s:form beanclass="cz.bbmri.action.request.CreateSampleQuestion" class="form-horizontal">

                <s:layout-render name="/webpages/request/create/createSampleQuestionForm.jsp"/>

                <div class="form-actions">
                        <s:link beanclass="cz.bbmri.action.project.ProjectActionBean"
                                event="detail" class="btn btn-inverse btnMargin">
                            <s:param name="projectId" value="${actionBean.projectId}"/>
                            <f:message key="cancel"/>
                        </s:link>

                    <security:allowed bean="createSampleQuestionBean" event="confirmSampleRequest">
                        <s:submit name="confirmSampleRequest" class="btn btn-primary">
                            <s:param name="projectId" value="${actionBean.projectId}"/>
                        </s:submit>
                    </security:allowed>
                </div>
            </s:form>

        </fieldset>


    </s:layout-component>
</s:layout-render>
