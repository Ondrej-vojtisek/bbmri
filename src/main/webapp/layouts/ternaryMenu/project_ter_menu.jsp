<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>
<s:useActionBean var="projectAttachmentsBean" beanclass="cz.bbmri.action.project.ProjectAttachmentsActionBean"/>
<s:useActionBean var="projectRequestsBean" beanclass="cz.bbmri.action.project.ProjectRequestActionBean"/>
<s:useActionBean var="projectAdministratorsBean" beanclass="cz.bbmri.action.project.ProjectAdministratorsActionBean"/>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="projectBean" event="detail">
    <li <c:if test="${ternarymenu == 'detail'}"> class="active" </c:if> >
        <s:link beanclass="cz.bbmri.action.project.ProjectActionBean" event="detail">
            <s:param name="projectId" value="${actionBean.projectId}"/>
            <f:message key="cz.bbmri.action.project.ProjectActionBean.basicData"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>
<security:allowed bean="projectAdministratorsBean" event="administratorsResolution">
    <li <c:if test="${ternarymenu == 'administrators'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.project.ProjectAdministratorsActionBean" event="administratorsResolution">
            <s:param name="projectId" value="${actionBean.projectId}"/>
            <f:message key="cz.bbmri.action.project.ProjectActionBean.administrators"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="projectAttachmentsBean" event="attachmentsResolution">
    <li <c:if test="${ternarymenu == 'attachments'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.project.ProjectAttachmentsActionBean" event="attachmentsResolution">
            <s:param name="projectId" value="${actionBean.projectId}"/>
            <f:message key="cz.bbmri.action.project.ProjectActionBean.attachments"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="projectRequestsBean" event="sampleRequestsResolution">
    <li <c:if test="${ternarymenu == 'sampleRequests'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.project.ProjectRequestActionBean" event="sampleRequestsResolution">
            <s:param name="projectId" value="${actionBean.projectId}"/>
            <f:message key="cz.bbmri.action.project.ProjectActionBean.sampleRequests"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>