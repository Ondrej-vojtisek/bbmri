<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.project.ProjectActionBean.createSampleRequest" var="title"/>
<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="sampleRequests">

    <s:layout-component name="body">

        <table class="table table-hover table-striped">

            <s:layout-render name="/webpages/component/detail/sampleQuestion/header.jsp"/>

            <tbody>

            <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             collection="${projectBean.project.sampleRequests}"/>

            <c:forEach items="${projectBean.project.sampleRequests}" var="sampleRequest">
                <tr>
                    <s:layout-render name="/webpages/component/detail/sampleQuestion/row.jsp"
                                     sampleQuestion="${sampleRequest}"/>

                    <td class="action">
                        <s:link beanclass="cz.bbmri.action.request.RequestActionBean" event="detail"
                                class="btn btn-primary">
                            <s:param name="sampleQuestionId" value="${sampleRequest.id}"/>
                            <s:param name="biobankId" value="${sampleRequest.biobank.id}"/>
                            <f:message key="detail"/>
                        </s:link>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </s:layout-component>
</s:layout-render>
