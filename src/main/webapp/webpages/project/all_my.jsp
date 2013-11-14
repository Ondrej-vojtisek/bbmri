<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.project.ProjectActionBean.myProjects" var="title"/>
<s:useActionBean var="projectBean" beanclass="bbmri.action.project.ProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_my_projects">

    <s:layout-component name="body">
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
                        <td class="action">
                            <c:set target="${projectBean}" property="id" value="${project.id}"/>

                            <security:allowed bean="projectBean" event="edit">
                                <div class="tableAction">
                                    <s:link beanclass="bbmri.action.project.ProjectActionBean" event="edit"
                                            class="btn btn-primary btnMargin">
                                        <s:param name="id" value="${project.id}"/>
                                        <f:message key="edit"/>
                                    </s:link>
                                </div>
                            </security:allowed>
                            <security:notAllowed bean="projectBean" event="edit">
                                <security:allowed bean="projectBean" event="detail">
                                    <div class="tableAction">
                                        <s:link beanclass="bbmri.action.project.ProjectActionBean" event="detail"
                                                class="btn btn-info btnMargin">
                                            <s:param name="id" value="${project.id}"/>
                                            <f:message key="detail"/>
                                        </s:link>
                                    </div>
                                </security:allowed>
                            </security:notAllowed>

                            <f:message var="question" key="bbmri.action.project.ProjectActionBean.questionDelete"/>

                            <security:allowed bean="projectBean" event="delete">
                                <s:form beanclass="bbmri.action.project.ProjectActionBean">
                                    <s:submit name="delete" class="btn btn-danger" onclick="return confirm('${question}')">
                                        <s:param name="id" value="${project.id}"/>
                                    </s:submit>
                                </s:form>
                            </security:allowed>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </s:form>

    </s:layout-component>
</s:layout-render>