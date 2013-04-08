<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="sample.request" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleQuestionActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="primary_menu">
        <li class="active"><s:link href="/project_my_projects.jsp" ><f:message key="projects"/></s:link></li>
        <li><s:link href="/biobank_all.jsp" ><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp" ><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp" ><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
        <li><s:link href="/project_my_projects.jsp"><f:message key="my_projects"/></s:link></li>
        <li><s:link beanclass="bbmri.action.Project.ProjectActionBean" event="createInitial"><f:message
                key="projects.createProject"/></s:link></li>
        <c:if test="${ab.loggedUser.biobank != null}">
            <li><s:link href="/project_approve.jsp"><f:message key="approve_project"/></s:link></li>
        </c:if>
        <li class="active"><s:link href="/samples_my_requests.jsp"><f:message key="my_requests"/></s:link></li>
        <c:if test="${ab.loggedUser.biobank != null || ab.loggedUser.administrator}">
            <li><s:link href="/project_all.jsp"><f:message key="all_projects"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.SampleQuestionActionBean">
            <c:forEach items="${ab.myProjects}" var="project">
                <fieldset>
                    <legend><f:message key="project.name"/>: <c:out value="${project.name}"/></legend>
                    <div><b><f:message key="project.fundingOrganization"/>:</b>
                        <c:out value="${project.fundingOrganization}"/></div>
                    <div><b><f:message key="project.owner"/>:</b>
                        <c:out value="${project.owner.wholeName}"/></div>
                    <div><b><f:message key="project.projectState"/>:</b>
                        <f:message key="ProjectState.${project.projectState}"/></div>
                </fieldset>

                <table id="sortableTable">
                    <thead>
                    <tr>
                        <th><f:message key="id"/></th>
                        <th><f:message key="requestGroup.created"/></th>
                        <th><f:message key="requestGroup.lastModification"/></th>
                        <th><f:message key="requestGroup.requestState"/></th>
                        <th><f:message key="biobank.name"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${project.requestGroups}" var="requestGroup">
                        <tr>
                            <td><c:out value="${requestGroup.id}"/></td>
                            <td><c:out value="${requestGroup.created}"/></td>
                            <td><c:out value="${requestGroup.lastModification}"/></td>
                            <td><f:message key="RequestState.${requestGroup.requestState}"/></td>
                            <td><c:out value="${requestGroup.biobank.name}"/></td>
                            <td><s:link beanclass="bbmri.action.SampleQuestionActionBean" event="requestGroupDetail">
                                <s:param name="requestGroup.id" value="${requestGroup.id}"/>
                                <f:message key="detail"/></s:link>
                            </td>
                        </tr>

                    </c:forEach>
                    </tbody>
                </table>

            </c:forEach>
        </s:form>

    </s:layout-component>
</s:layout-render>