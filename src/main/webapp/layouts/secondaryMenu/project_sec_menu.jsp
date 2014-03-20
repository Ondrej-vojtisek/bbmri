<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>

<s:layout-definition>
<%-- -------------------------------------------------------------------- --%>

<li <c:if test="${secondarymenu == 'project_my_projects'}"> class="active" </c:if> ><s:link
        beanclass="${projectBean.name}"><f:message
        key="my_projects"/></s:link></li>

<%-- -------------------------------------------------------------------- --%>

<li <c:if test="${secondarymenu == 'project_create_project'}"> class="active" </c:if> >
    <s:link beanclass="cz.bbmri.action.project.CreateProjectActionBean"
            event="initial"><f:message key="projects.createProject"/></s:link></li>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="projectBean" event="display">
    <li <c:if test="${secondarymenu == 'project_all'}"> class="active" </c:if> ><s:link
            beanclass="${projectBean.name}" event="display"><f:message
            key="all_projects"/></s:link></li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

</s:layout-definition>