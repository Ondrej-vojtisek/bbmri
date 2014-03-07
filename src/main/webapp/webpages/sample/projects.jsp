<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.sample.SampleActionBean.detail" var="title"/>
<s:useActionBean var="sampleBean" beanclass="cz.bbmri.action.sample.SampleActionBean"/>
<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="sample"
                 ternarymenu="projects">

    <s:layout-component name="body">

        <table class="table table-hover table-striped">
            <s:layout-render name="/webpages/component/detail/project/header.jsp"/>
            <tbody>

            <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             collection="${actionBean.projectsBySample}"/>

            <c:forEach items="${actionBean.projectsBySample}" var="project">
                <tr>
                    <s:layout-render name="/webpages/component/detail/project/row.jsp" record="${project}"/>

                    <td class="action">
                    <c:set target="${projectBean}" property="projectId" value="${project.id}"/>

                    <security:allowed bean="projectBean" event="detail">
                        <div class="tableAction">
                            <s:link beanclass="cz.bbmri.action.project.ProjectActionBean" event="detail"
                                    class="btn btn-info btnMargin">
                                <s:param name="projectId" value="${project.id}"/>
                                <f:message key="detail"/>
                            </s:link>
                        </div>
                    </security:allowed>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </s:layout-component>
</s:layout-render>