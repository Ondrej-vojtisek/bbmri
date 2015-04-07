<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.action.request.RequestActionBean.title" var="title"/>
<stripes:useActionBean var="requestBean" beanclass="cz.bbmri.action.request.RequestActionBean"/>
<stripes:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>

<stripes:layout-render name="/layouts/layout_content.jsp" title="${title}">

    <stripes:layout-component name="body">
        <fieldset>
            <legend></legend>
            <stripes:form beanclass="${requestBean.name}" class="form-horizontal">

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.SampleQuestion.biobank" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="sampleQuestion.biobank.name" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.SampleQuestion.created" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="sampleQuestion.created" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.SampleQuestion.requestState" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="sampleQuestion.requestState" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.SampleQuestion.specification" class="control-label"/>
                    <div class="controls">
                        <stripes:textarea name="sampleQuestion.specification" readonly="true"/>
                    </div>
                </div>

                <core:if test="${actionBean.isSampleQuestionNew}">

                    <div class="form-actions">

                        <stripes:hidden name="sampleQuestionId" value="${requestBean.sampleQuestionId}"/>

                        <security:allowed bean="requestBean" event="approve">
                            <stripes:submit name="approve" class="btn btn-primary btnMargin"/>
                        </security:allowed>

                        <%--<security:allowed bean="requestBean" event="deny">--%>
                            <%--<stripes:submit name="deny" class="btn btn-primary btnMargin"/>--%>
                        <%--</security:allowed>--%>

                        <%--<security:allowed bean="requestBean" event="delete">--%>
                            <%--<stripes:submit name="delete" class="btn btn-danger btnMargin"/>--%>
                        <%--</security:allowed>--%>

                    </div>

                </core:if>

            </stripes:form>


        </fieldset>

        <core:if test="${not requestBean.isSampleQuestionNew and not requestBean.isSampleQuestionDenied}">
            <stripes:layout-render name="/webpages/request/requests.jsp"/>
        </core:if>


    </stripes:layout-component>
</stripes:layout-render>
