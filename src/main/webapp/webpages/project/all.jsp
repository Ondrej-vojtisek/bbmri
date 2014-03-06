<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.project.ProjectActionBean.allProjects" var="title"/>
<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_all">

    <s:layout-component name="body">


        <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                eventName="detail" paramName="projectId"/>

        <%--<table class="table table-hover table-striped">--%>
            <%--<s:layout-render name="/webpages/component/detail/project/header.jsp"/>--%>
            <%--<tbody>--%>

            <%--<s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"--%>
                                           <%--collection="${projectBean.all}"/>--%>

            <%--<c:forEach items="${projectBean.all}" var="project">--%>
                <%--<tr>--%>

                    <%--<s:layout-render name="/webpages/component/detail/project/row.jsp" project="${project}"/>--%>

                    <%--<td class="action">--%>

                        <%--<c:set target="${projectBean}" property="projectId" value="${project.id}"/>--%>

                        <%--<security:allowed bean="projectBean" event="detail">--%>
                            <%--<div class="tableAction">--%>
                                <%--<s:link beanclass="cz.bbmri.action.project.ProjectActionBean" event="detail"--%>
                                        <%--class="btn btn-info btnMargin">--%>
                                    <%--<s:param name="projectId" value="${project.id}"/>--%>
                                    <%--<f:message key="detail"/>--%>
                                <%--</s:link>--%>
                            <%--</div>--%>
                        <%--</security:allowed>--%>

                        <%--<security:allowed bean="projectBean" event="delete">--%>
                            <%--<s:form beanclass="cz.bbmri.action.project.ProjectActionBean">--%>

                                <%--<f:message var="question"--%>
                                           <%--key="cz.bbmri.action.project.ProjectActionBean.questionDelete"/>--%>

                                <%--<s:submit name="delete" class="btn btn-danger" onclick="return confirm('${question}')">--%>
                                    <%--<s:param name="projectId" value="${project.id}"/>--%>
                                <%--</s:submit>--%>
                            <%--</s:form>--%>
                        <%--</security:allowed>--%>

                    <%--</td>--%>
                <%--</tr>--%>
            <%--</c:forEach>--%>
            <%--</tbody>--%>
        <%--</table>--%>

    </s:layout-component>
</s:layout-render>