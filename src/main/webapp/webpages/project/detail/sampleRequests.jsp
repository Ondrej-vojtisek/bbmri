<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.project.ProjectActionBean.createSampleRequest" var="title"/>
<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="sampleRequests">

    <s:layout-component name="body">

        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th><f:message key="cz.bbmri.entities.SampleRequest.created"/></th>
                <th><f:message key="cz.bbmri.entities.SampleRequest.biobank"/></th>
                <th><f:message key="cz.bbmri.entities.SampleRequest.requestState"/></th>
                <th><f:message key="cz.bbmri.entities.SampleRequest.specification"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${empty projectBean.sampleRequests}">
                <tr>
                    <td colspan="6"><f:message key="empty"/></td>
                </tr>
            </c:if>

            <c:forEach items="${projectBean.sampleRequests}" var="sampleRequest">
                <tr>
                    <td>${sampleRequest.created}</td>
                    <td>${sampleRequest.biobank.name}</td>
                    <td><f:message key="cz.bbmri.entities.enumeration.RequestState.${sampleRequest.requestState}"/></td>
                    <td>${sampleRequest.specification}</td>
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
