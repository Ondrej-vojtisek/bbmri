<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.request.RequestActionBean.title" var="title"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}">

    <s:layout-component name="body">

        Choose project
        getMyApprovedProjects
        <s:form beanclass="cz.bbmri.action.request.RequestActionBean">
            <div class="form-actions">

                <s:hidden name="sampleQuestionId" value="${actionBean.sampleQuestionId}"/>

                <div class="action">
                <s:select name="projectId">
                    <s:options-collection collection="${actionBean.myApprovedProjects}"
                                          label="name" value="id"/>
                </s:select>

                <s:submit name="asignToProject" class="btn btn-primary btnMargin"/>
                </div>
            </div>


        </s:form>

    </s:layout-component>
</s:layout-render>
