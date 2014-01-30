<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="projectBean" event="detail">
    <li <c:if test="${ternarymenu == 'detail'}"> class="active" </c:if> >
        <s:link beanclass="cz.bbmri.action.project.ProjectActionBean" event="detail">
            <s:param name="projectId" value="${projectBean.projectId}"/>
            <f:message key="cz.bbmri.action.project.ProjectActionBean.basicData"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<%--Event from projectBean is here only because security check--%>

<security:allowed bean="projectBean" event="editAdministrators">
    <li <c:if test="${ternarymenu == 'addAdministrator'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.project.FindProjectAdminActionBean">
            <s:param name="projectId" value="${projectBean.projectId}"/>
            <f:message key="cz.bbmri.action.project.ProjectActionBean.addAdministrator"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>
<security:allowed bean="projectBean" event="administratorsResolution">
    <li <c:if test="${ternarymenu == 'administrators'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.project.ProjectActionBean" event="administratorsResolution">
            <s:param name="projectId" value="${projectBean.projectId}"/>
            <f:message key="cz.bbmri.action.project.ProjectActionBean.administrators"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="projectBean" event="attachments">
    <li <c:if test="${ternarymenu == 'attachments'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.project.ProjectActionBean" event="attachments">
            <s:param name="projectId" value="${projectBean.projectId}"/>
            <f:message key="cz.bbmri.action.project.ProjectActionBean.attachments"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="projectBean" event="sampleRequests">
    <li <c:if test="${ternarymenu == 'sampleRequests'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.project.ProjectActionBean" event="sampleRequests">
            <s:param name="projectId" value="${projectBean.projectId}"/>
            <f:message key="cz.bbmri.action.project.ProjectActionBean.sampleRequests"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>