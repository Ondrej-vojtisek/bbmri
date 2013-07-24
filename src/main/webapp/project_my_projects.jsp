<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="projects.MyProjects" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Project.ProjectActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.wholeName}"
                 primarymenu="project"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}"
                 secondarymenu="project_my_projects">

    <s:layout-component name="body">
        <fieldset>
            <legend><f:message key="project.all_projects"/></legend>
            <s:form beanclass="bbmri.action.Project.ProjectActionBean">
                <table cellspacing="0" class="tablesorter">
                    <thead>
                    <tr>
                        <th><s:label name="project.name"/></th>
                        <th><s:label name="project.annotation"/></th>
                        <th><s:label name="project.fundingOrganization"/></th>
                        <th><s:label name="project.owner"/></th>
                        <th><s:label name="project.projectState"/></th>
                        <th colspan="3" class="noSort"><s:label name="actions"/></th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${ab.myProjects}" var="project">
                        <tr>
                            <td><c:out value="${project.name}"/></td>
                            <td><c:out value="${project.annotation}"/></td>
                            <td><c:out value="${project.fundingOrganization}"/></td>
                            <td><c:out value="${project.owner.wholeName}"/></td>

                            <td><f:message key="ProjectState.${project.projectState}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${fn:contains(project.users, ab.loggedUser) &&
                                        !fn:ownProject(project, ab.loggedUser)}">
                                        <s:link beanclass="bbmri.action.Project.ProjectActionBean" event="leave">
                                            <s:param name="project.id" value="${project.id}"/><f:message
                                                key="project.leave"/></s:link>
                                    </c:when>
                                    <c:otherwise>
                                        <f:message key="project.leave"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${fn:contains(project.users, ab.loggedUser) &&project.projectState != 'NEW'
                                         && project.projectState != null}">
                                        <s:link beanclass="bbmri.action.Project.ProjectActionBean"
                                                event="requestSample">
                                            <s:param name="project.id" value="${project.id}"/><f:message
                                                key="request_sample"/></s:link>
                                    </c:when>
                                    <c:otherwise>
                                        <f:message key="request_sample"/>
                                    </c:otherwise>
                                </c:choose>


                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${fn:ownProject(project, ab.loggedUser)}">
                                        <s:link beanclass="bbmri.action.Project.ProjectActionBean" event="edit">
                                            <s:param name="project.id" value="${project.id}"/><f:message
                                                key="edit"/></s:link>
                                    </c:when>
                                    <c:otherwise>
                                        <f:message key="edit"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </s:form>
        </fieldset>


    </s:layout-component>
</s:layout-render>