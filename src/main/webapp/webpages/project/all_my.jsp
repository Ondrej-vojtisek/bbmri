<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.project.ProjectActionBean.myProjects" var="title"/>
<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_my_projects">

    <s:layout-component name="body">
        <s:form beanclass="cz.bbmri.action.project.ProjectActionBean">

            <table class="table table-hover table-striped">

                <s:layout-render name="/webpages/component/detail/project/header.jsp"/>

                <tbody>

                <c:if test="${empty projectBean.myProjects}">
                    <tr>
                        <td colspan="5"><f:message key="empty"/></td>
                    </tr>
                </c:if>

                <c:forEach items="${projectBean.myProjects}" var="project">
                    <tr>

                        <s:layout-render name="/webpages/component/detail/project/row.jsp" project="${project}"/>

                        <td class="action">
                            <c:set target="${projectBean}" property="projectId" value="${project.id}"/>

                            <security:allowed bean="projectBean" event="edit">
                                <div class="tableAction">
                                    <s:link beanclass="cz.bbmri.action.project.ProjectActionBean" event="edit"
                                            class="btn btn-primary btnMargin">
                                        <s:param name="projectId" value="${project.id}"/>
                                        <f:message key="edit"/>
                                    </s:link>
                                </div>
                            </security:allowed>
                            <security:notAllowed bean="projectBean" event="edit">
                                <security:allowed bean="projectBean" event="detail">
                                    <div class="tableAction">
                                        <s:link beanclass="cz.bbmri.action.project.ProjectActionBean" event="detail"
                                                class="btn btn-info btnMargin">
                                            <s:param name="projectId" value="${project.id}"/>
                                            <f:message key="detail"/>
                                        </s:link>
                                    </div>
                                </security:allowed>
                            </security:notAllowed>

                            <f:message var="question" key="cz.bbmri.action.project.ProjectActionBean.questionDelete"/>

                            <security:allowed bean="projectBean" event="delete">
                                <s:form beanclass="cz.bbmri.action.project.ProjectActionBean">
                                    <s:submit name="delete" class="btn btn-danger"
                                              onclick="return confirm('${question}')">
                                        <s:param name="projectId" value="${project.id}"/>
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