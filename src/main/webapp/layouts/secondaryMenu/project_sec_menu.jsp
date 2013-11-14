<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="projectBean" beanclass="bbmri.action.project.ProjectActionBean"/>

<%------------------------------------------------------------------------%>

<li <c:if test="${secondarymenu == 'project_my_projects'}"> class="active" </c:if> ><s:link
        beanclass="${projectBean.name}"><f:message
        key="my_projects"/></s:link></li>

<%------------------------------------------------------------------------%>

<li <c:if test="${secondarymenu == 'project_create_project'}"> class="active" </c:if> >
    <s:link beanclass="bbmri.action.project.CreateProjectActionBean"
            event="initial"><f:message key="projects.createProject"/></s:link></li>

<%------------------------------------------------------------------------%>

<%--<c:if test="${not empty biobank}">--%>
    <%--<li <c:if test="${secondarymenu == 'project_approve'}"> class="active" </c:if> ><s:link--%>
            <%--beanclass="bbmri.action.project.ApproveProjectActionBean"><f:message--%>
            <%--key="approve_project"/></s:link></li>--%>
<%--</c:if>--%>

<%--<li <c:if test="${secondarymenu == 'samples_my_requests'}"> class="active" </c:if> ><s:link--%>
        <%--beanclass="bbmri.action.SampleQuestionActionBean" event="myRequests"><f:message--%>
        <%--key="my_requests"/></s:link></li>--%>

<%------------------------------------------------------------------------%>

<security:allowed bean="projectBean" event="allProjects">
    <li <c:if test="${secondarymenu == 'project_all'}"> class="active" </c:if> ><s:link
            beanclass="${projectBean.name}" event="allProjects"><f:message
            key="all_projects"/></s:link></li>
</security:allowed>

<%------------------------------------------------------------------------%>