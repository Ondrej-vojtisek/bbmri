<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="project.all_projects" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Project.ProjectActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}"
                 primarymenu="project"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}"
                 secondarymenu="project_all">

    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="project.all_projects"/></legend>
            <s:form beanclass="bbmri.action.Project.ProjectActionBean">
                <table id="sortableTable">
                    <thead>
                    <tr>
                        <th><s:label name="project.name"/></th>
                        <th><s:label name="project.annotation"/></th>
                        <th><s:label name="project.fundingOrganization"/></th>
                        <th><s:label name="project.owner"/></th>
                        <th><s:label name="project.projectState"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${ab.projects}" var="project">
                        <tr>
                            <td><c:out value="${project.name}"/></td>
                            <td><c:out value="${project.annotation}"/></td>
                            <td><c:out value="${project.fundingOrganization}"/></td>
                            <td><c:out value="${project.owner.wholeName}"/></td>

                            <td><f:message key="ProjectState.${project.projectState}"/></td>
                            <td><c:if test="${fn:contains(project.users, ab.loggedUser)}">
                                <c:if test="${!fn:ownProject(project, ab.loggedUser)}">
                                    <s:link beanclass="bbmri.action.Project.ProjectActionBean" event="leave">
                                        <s:param name="project.id" value="${project.id}"/><f:message
                                            key="project.leave"/></s:link>
                                </c:if>
                            </c:if>
                            </td>

                            <td><c:if test="${fn:contains(project.users, ab.loggedUser)}">
                                <c:if test="${project.projectState != 'NEW' && project.projectState != null}">
                                    <s:link beanclass="bbmri.action.Project.ProjectActionBean"
                                            event="requestSample">
                                        <s:param name="project.id" value="${project.id}"/><f:message
                                            key="request_sample"/></s:link>
                                </c:if>
                            </c:if>
                            </td>

                            <td><c:if test="${fn:ownProject(project, ab.loggedUser)}">
                                <s:link beanclass="bbmri.action.Project.ProjectActionBean" event="edit">
                                    <s:param name="project.id" value="${project.id}"/><f:message
                                        key="edit"/></s:link>
                            </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </s:form>
        </fieldset>

    </s:layout-component>
</s:layout-render>