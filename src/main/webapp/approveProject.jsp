<%@ page import="bbmri.entities.ProjectState" %>
<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="projects_to_approve" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Project.ApproveProjectActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="project.all_projects"/></legend>
            <s:form beanclass="bbmri.action.Project.ApproveProjectActionBean">
                <table border="1">
                    <tr>
                        <th><s:label name="project.id"/></th>
                        <th><s:label name="project.name"/></th>
                        <th><s:label name="project.description"/></th>
                        <th><s:label name="project.fundingOrganization"/></th>
                        <th><s:label name="project.owner"/></th>
                        <th><s:label name="project.projectState"/></th>
                    </tr>

                    <c:forEach items="${ab.projects}" var="project">
                        <c:if test="${project.projectState == 'NEW'}">

                            <tr>
                                <td><c:out value="${project.id}"/></td>
                                <td><c:out value="${project.name}"/></td>
                                <td><c:out value="${project.description}"/></td>
                                <td><c:out value="${project.fundingOrganization}"/></td>
                                <td><c:out value="${project.owner}"/></td>

                                <td><f:message key="ProjectState.${project.projectState}"/></td>
                                <td>
                                    <s:link beanclass="bbmri.action.Project.ApproveProjectActionBean" event="approve">
                                        <s:param name="project.id" value="${project.id}"/>
                                        <f:message key="approve"/></s:link>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </s:form>
        </fieldset>

    </s:layout-component>
</s:layout-render>