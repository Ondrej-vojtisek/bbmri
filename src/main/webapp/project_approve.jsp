<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="projects_to_approve" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Project.ApproveProjectActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}"
                 primarymenu="project"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}"
                 secondarymenu="project_approve">

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