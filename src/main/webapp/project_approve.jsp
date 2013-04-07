<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="projects_to_approve" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Project.ApproveProjectActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}">

    <s:layout-component name="primary_menu">
        <li class="active"><s:link href="/project_my_projects.jsp"><f:message key="projects"/></s:link></li>
        <li><s:link href="/biobank_all.jsp"><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp"><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp"><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
        <li><s:link href="/project_my_projects.jsp"><f:message key="my_projects"/></s:link></li>
        <li><s:link  beanclass="bbmri.action.Project.ProjectActionBean" event="createInitial"><f:message key="projects.createProject"/></s:link></li>
        <c:if test="${ab.loggedUser.biobank != null}">
            <li class="active"><s:link href="/project_approve.jsp"><f:message key="approve"/></s:link></li>
        </c:if>
        <li><s:link href="/samples_my_requests.jsp"><f:message key="my_requests"/></s:link></li>
        <c:if test="${ab.loggedUser.biobank != null || ab.loggedUser.administrator}">
        <li><s:link href="/project_all.jsp"><f:message key="all_projects"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="body">
        <fieldset>
            <legend><f:message key="projects_to_approve"/></legend>
            <s:form beanclass="bbmri.action.Project.ApproveProjectActionBean">
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
                        <c:if test="${project.projectState == 'NEW'}">
                            <tr>
                                <td><c:out value="${project.name}"/></td>
                                <td><c:out value="${project.annotation}"/></td>
                                <td><c:out value="${project.fundingOrganization}"/></td>
                                <td><c:out value="${project.owner.wholeName}"/></td>
                                <td><f:message key="ProjectState.${project.projectState}"/></td>
                                <td><s:link beanclass="bbmri.action.Project.ApproveProjectActionBean" event="detail">
                                    <s:param name="project.id" value="${project.id}"/>
                                    <f:message key="detail"/></s:link>
                                </td>
                                <td><s:link beanclass="bbmri.action.Project.ApproveProjectActionBean" event="approve">
                                    <s:param name="project.id" value="${project.id}"/>
                                    <f:message key="approve"/></s:link>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
            </s:form>
        </fieldset>
    </s:layout-component>
</s:layout-render>