<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="projectActionBean" beanclass="cz.bbmri.action.ProjectActionBean"/>

<stripes:layout-definition>

    <ul class="nav nav-tabs">

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="projectActionBean" event="detail">
            <li <core:if test="${active == 'detail'}"> class="active" </core:if> >
                <stripes:link beanclass="cz.bbmri.action.ProjectActionBean" event="detail">
                    <stripes:param name="id" value="${actionBean.id}"/>
                    <format:message key="cz.bbmri.action.project.ProjectActionBean.basicData"/>
                </stripes:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>
        <security:allowed bean="projectActionBean" event="projectuser">
            <li <core:if test="${active == 'projectuser'}"> class="active" </core:if>>
                <stripes:link beanclass="cz.bbmri.action.ProjectActionBean"
                        event="projectuser">
                    <stripes:param name="id" value="${actionBean.id}"/>
                    <format:message key="cz.bbmri.entity.ProjectUser.projectUsers"/>
                </stripes:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="projectActionBean" event="attachments">
            <li <core:if test="${active == 'attachments'}"> class="active" </core:if>>
                <stripes:link beanclass="cz.bbmri.action.ProjectActionBean" event="attachments">
                    <stripes:param name="id" value="${actionBean.id}"/>
                    <format:message key="cz.bbmri.entity.Attachment.attachments"/>
                </stripes:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>
        <security:allowed bean="projectActionBean" event="questions">
            <li <core:if test="${active == 'questions'}"> class="active" </core:if>>
                <stripes:link beanclass="cz.bbmri.action.ProjectActionBean" event="questions">
                    <stripes:param name="id" value="${actionBean.id}"/>
                    <format:message key="cz.bbmri.entity.Question.questions"/>
                </stripes:link>
            </li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

    </ul>

</stripes:layout-definition>