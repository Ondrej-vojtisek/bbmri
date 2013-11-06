<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="project.project_my_projects" var="title"/>
<s:useActionBean var="projectBean" beanclass="bbmri.action.project.ProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_my_projects">

    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="project_my_projects"/></legend>
            <s:form beanclass="bbmri.action.project.ProjectActionBean">
                <table class="table table-hover table-striped">
                    <thead>
                    <tr>
                        <th><s:label name="project.name"/></th>
                        <th><s:label name="project.fundingOrganization"/></th>
                        <th><s:label name="project.projectState"/></th>

                    </tr>
                    </thead>
                    <tbody>

                    <c:if test="${empty projectBean.myProjects}">
                        <tr>
                            <td colspan="5"><f:message key="empty"/></td>
                        </tr>
                    </c:if>

                    <c:forEach items="${projectBean.myProjects}" var="project">
                        <tr>
                            <td><c:out value="${project.name}"/></td>
                            <td><c:out value="${project.fundingOrganization}"/></td>
                            <td><f:message key="ProjectState.${project.projectState}"/></td>
                            <td>
                                <c:set target="${projectBean}" property="id" value="${project.id}"/>

                                <security:allowed bean="projectBean" event="edit">
                                    <s:link beanclass="bbmri.action.project.ProjectActionBean" event="edit">
                                        <s:param name="id" value="${project.id}"/><f:message
                                            key="edit"/></s:link>
                                </security:allowed>
                                <security:notAllowed bean="projectBean" event="edit">
                                    <security:allowed bean="projectBean" event="detail">
                                        <s:link beanclass="bbmri.action.project.ProjectActionBean" event="detail">
                                            <s:param name="id" value="${project.id}"/><f:message
                                                key="detail"/></s:link>
                                    </security:allowed>
                                </security:notAllowed>

                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </s:form>
        </fieldset>

    </s:layout-component>
</s:layout-render>