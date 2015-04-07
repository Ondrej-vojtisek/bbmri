<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.action.request.RequestActionBean.title" var="title"/>

<stripes:layout-render name="/layouts/layout_content.jsp" title="${title}">

    <stripes:layout-component name="body">

        Choose project
        getMyApprovedProjects
        <stripes:form beanclass="cz.bbmri.action.request.RequestActionBean">
            <div class="form-actions">

                <stripes:hidden name="sampleQuestionId" value="${actionBean.sampleQuestionId}"/>

                <div class="action">
                <stripes:select name="projectId">
                    <stripes:options-collection collection="${actionBean.myApprovedProjects}"
                                          label="name" value="id"/>
                </stripes:select>

                <stripes:submit name="asignToProject" class="btn btn-primary btnMargin"/>
                </div>
            </div>


        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>
