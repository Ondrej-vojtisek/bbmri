<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="projectActionBean" beanclass="cz.bbmri.action.ProjectActionBean"/>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.project}" active="detail"/>

        <stripes:layout-render name="/webpages/new/project/component/hasMta.jsp"/>

        <stripes:form beanclass="cz.bbmri.action.ProjectActionBean" class="form-horizontal">
            <div class="form-actions">
                <stripes:hidden name="id" value="${actionBean.project.id}"/>

                <core:if test="${actionBean.project.isNew}">
                    <security:allowed bean="projectActionBean" event="confirm">
                        <stripes:submit name="confirm" class="btn btn-primary btnMargin"/>
                    </security:allowed>

                    <security:allowed bean="projectActionBean" event="deny">
                        <stripes:submit name="deny" class="btn btn-danger btnMargin"/>
                    </security:allowed>
                </core:if>

                <core:if test="${actionBean.project.isConfirmed}">
                    <security:allowed bean="projectActionBean" event="finish">
                        <stripes:submit name="finish" class="btn btn-primary btnMargin"/>
                    </security:allowed>

                    <security:allowed bean="projectActionBean" event="cancel">
                        <stripes:submit name="cancel" class="btn btn-danger btnMargin"/>
                    </security:allowed>
                </core:if>

            </div>
        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>