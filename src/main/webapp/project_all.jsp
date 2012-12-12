<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fn" uri="/WEB-INF/custom-functions.tld" %>

<f:message key="project.all_projects" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Project.AllProjectsActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedUser.name}">

    <s:layout-component name="primary_menu">
        <li class="active"><s:link href="/project_all.jsp"><f:message key="projects"/></s:link></li>
        <li><s:link href="/biobank_all.jsp"><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp"><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp"><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
        <li class="active"><s:link href="/project_all.jsp"><f:message key="all"/></s:link></li>
        <li><s:link href="/project_create.jsp"><f:message key="projects.createProject"/></s:link></li>
        <c:if test="${ab.loggedUser.ethicalCommitteeOfBiobank != null}">
            <li><s:link href="/project_approve.jsp"><f:message key="approve"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="project.all_projects"/></legend>
            <s:form beanclass="bbmri.action.Project.AllProjectsActionBean">
                <table border="1">
                    <tr>
                        <th><s:label name="project.id"/></th>
                        <th><s:label name="project.name"/></th>
                        <th><s:label name="project.description"/></th>
                        <th><s:label name="project.fundingOrganization"/></th>
                        <th><s:label name="project.owner"/></th>
                        <th><s:label name="project.projectState"/></th>
                        <th colspan="4"><s:label name="actions"/></th>
                    </tr>

                    <c:forEach items="${ab.projects}" var="project">
                        <tr>
                            <td><c:out value="${project.id}"/></td>
                            <td><c:out value="${project.name}"/></td>
                            <td><c:out value="${project.description}"/></td>
                            <td><c:out value="${project.fundingOrganization}"/></td>
                            <td><c:out value="${project.owner}"/></td>

                            <td><f:message key="ProjectState.${project.projectState}"/></td>
                            <td>
                                <c:if test="${ !fn:contains(project.users, ab.loggedUser)}">
                                    <s:link beanclass="bbmri.action.Project.AllProjectsActionBean" event="join">
                                        <s:param name="project.id" value="${project.id}"/><f:message
                                            key="join"/></s:link>

                                </c:if>
                            </td>
                            <td>
                                <c:if test="${fn:contains(project.users, ab.loggedUser)}">
                                    <c:if test="${!fn:ownProject(project, ab.loggedUser)}">
                                        <s:link beanclass="bbmri.action.Project.AllProjectsActionBean" event="leave">
                                            <s:param name="project.id" value="${project.id}"/><f:message
                                                key="project.leave"/></s:link>
                                    </c:if>
                                </c:if>
                            </td>

                            <td><c:if test="${fn:contains(project.users, ab.loggedUser)}">
                                <c:if test="${project.projectState != 'NEW' && project.projectState != null}">
                                    <s:link beanclass="bbmri.action.Project.AllProjectsActionBean"
                                            event="requestSample">
                                        <s:param name="project.id" value="${project.id}"/><f:message
                                            key="request_sample"/></s:link>
                                </c:if>
                            </c:if>
                            </td>

                            <td>
                                <c:if test="${fn:ownProject(project, ab.loggedUser)}">

                                    <s:link beanclass="bbmri.action.Project.AllProjectsActionBean" event="edit">
                                        <s:param name="project.id" value="${project.id}"/><f:message
                                            key="edit"/></s:link>
                                </c:if>

                            </td>

                        </tr>
                    </c:forEach>
                </table>
            </s:form>
        </fieldset>

    </s:layout-component>
</s:layout-render>