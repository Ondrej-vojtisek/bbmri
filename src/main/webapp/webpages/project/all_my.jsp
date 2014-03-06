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

                <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                                               collection="${projectBean.loggedUser.projectAdministrators}"/>
                <c:if test="${not empty projectBean.loggedUser.projectAdministrators}">

                <c:forEach items="${projectBean.loggedUser.projectAdministrators}" var="pa">
                    <tr>

                        <s:layout-render name="/webpages/component/detail/project/row.jsp" record="${pa.project}"/>

                        <td class="action">
                            <c:set target="${projectBean}" property="projectId" value="${pa.project.id}"/>

                            <security:allowed bean="projectBean" event="detail">
                            <div class="tableAction">
                                <s:link beanclass="cz.bbmri.action.project.ProjectActionBean" event="detail"
                                        class="btn btn-info btnMargin">
                                    <s:param name="projectId" value="${pa.project.id}"/>
                                    <f:message key="detail"/>
                                </s:link>
                            </div>
                        </security:allowed>

                            <f:message var="question" key="cz.bbmri.action.project.ProjectActionBean.questionDelete"/>

                            <security:allowed bean="projectBean" event="delete">
                                <s:form beanclass="cz.bbmri.action.project.ProjectActionBean">
                                    <s:submit name="delete" class="btn btn-danger"
                                              onclick="return confirm('${question}')">
                                        <s:param name="projectId" value="${pa.project.id}"/>
                                    </s:submit>
                                </s:form>
                            </security:allowed>

                        </td>
                    </tr>
                </c:forEach>
                </c:if>
                </tbody>
            </table>
        </s:form>

    </s:layout-component>
</s:layout-render>