<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.project.ProjectActionBean.createSampleRequest" var="title"/>
<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="sampleRequests">

    <s:layout-component name="body">

        <table class="table table-hover table-striped">

            <s:layout-render name="/webpages/component/detail/sampleRequest/header.jsp"/>

            <tbody>
            <c:if test="${empty projectBean.sampleRequests}">
                <tr>
                    <td colspan="6"><f:message key="empty"/></td>
                </tr>
            </c:if>

            <c:forEach items="${projectBean.sampleRequests}" var="sampleRequest">
                <tr>

                    <s:layout-render name="/webpages/component/detail/sampleRequest/row.jsp" sampleRequest="${sampleRequest}"/>

                    <td class="action">
                        <s:link beanclass="cz.bbmri.action.request.RequestActionBean" event="detail" class="btn btn-primary">
                            <s:param name="sampleRequestId" value="${sampleRequest.id}"/>
                            <f:message key="detail"/>
                        </s:link>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </s:layout-component>
</s:layout-render>
