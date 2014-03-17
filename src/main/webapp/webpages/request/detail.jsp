<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.request.RequestActionBean.title" var="title"/>
<s:useActionBean var="requestBean" beanclass="cz.bbmri.action.request.RequestActionBean"/>
<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}">

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

                <c:if test="${actionBean.isSampleQuestionNew}">

                    <div class="form-actions">

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


                        <security:allowed bean="requestBean" event="delete">
                            <s:submit name="delete" class="btn btn-danger btnMargin">
                                <s:param name="sampleQuestionId" value="${requestBean.sampleQuestionId}"/>
                            </s:submit>
                        </security:allowed>

                    </div>

                </c:if>

            </s:form>


        </fieldset>

        <c:if test="${not requestBean.isSampleQuestionNew and not requestBean.isSampleQuestionDenied}">
            <s:layout-render name="/webpages/request/requests.jsp"/>
        </c:if>


    </s:layout-component>
</s:layout-render>
