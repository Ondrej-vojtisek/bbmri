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
                    <s:label for="cz.bbmri.entities.SampleRequest.biobank" class="control-label"/>
                    <div class="controls">
                        <s:text name="sampleRequest.biobank.name" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entities.SampleRequest.created" class="control-label"/>
                    <div class="controls">
                        <s:text name="sampleRequest.created" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entities.SampleRequest.requestState" class="control-label"/>
                    <div class="controls">
                        <s:text name="sampleRequest.requestState" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entities.SampleRequest.specification" class="control-label"/>
                    <div class="controls">
                        <s:textarea name="sampleRequest.specification" readonly="true"/>
                    </div>
                </div>

                <div class="form-actions">
                    <s:link beanclass="${projectBean.name}" event="detail" class="btn btn-inverse btnMargin">
                        <s:param name="projectId" value="${requestBean.sampleRequest.project.id}"/>
                        <f:message key="cz.bbmri.action.request.RequestActionBean.returnToProject"/>
                    </s:link>

                    <c:if test="${requestBean.isSampleRequestNew}">

                        <security:allowed bean="requestBean" event="approve">
                            <s:submit name="approve" class="btn btn-primary btnMargin">
                                <s:param name="sampleRequestId" value="${requestBean.sampleRequestId}"/>
                            </s:submit>
                        </security:allowed>

                        <security:allowed bean="requestBean" event="deny">
                            <s:submit name="deny" class="btn btn-primary btnMargin">
                                <s:param name="sampleRequestId" value="${requestBean.sampleRequestId}"/>
                            </s:submit>
                        </security:allowed>


                        <security:allowed bean="requestBean" event="delete">
                            <s:submit name="delete" class="btn btn-danger btnMargin">
                                <s:param name="sampleRequestId" value="${requestBean.sampleRequestId}"/>
                            </s:submit>
                        </security:allowed>

                    </c:if>
                </div>

            </s:form>


        </fieldset>

        <c:if test="${requestBean.isSampleRequestApproved}">
            <jsp:include page="/webpages/request/requestGroups.jsp"/>
        </c:if>


    </s:layout-component>
</s:layout-render>
