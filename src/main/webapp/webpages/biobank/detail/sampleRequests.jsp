<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.detail" var="title"/>
<s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="sampleRequests">

    <s:layout-component name="body">

        <s:layout-component name="body">

            <table class="table table-hover table-striped">

                <s:layout-render name="/webpages/component/detail/sampleRequest/header.jsp"/>

                <tbody>
                <c:if test="${empty biobankBean.sampleRequests}">
                    <tr>
                        <td colspan="6"><f:message key="empty"/></td>
                    </tr>
                </c:if>

                <c:forEach items="${biobankBean.sampleRequests}" var="sampleRequest">
                    <tr>

                        <s:layout-render name="/webpages/component/detail/sampleRequest/row.jsp" sampleRequest="${sampleRequest}"/>

                        <td class="action">
                            <s:link beanclass="cz.bbmri.action.request.RequestActionBean" event="detail"
                                    class="btn btn-primary">
                                <s:param name="sampleRequestId" value="${sampleRequest.id}"/>
                                <s:param name="biobankId" value="${biobankBean.biobankId}"/>
                                <f:message key="detail"/>
                            </s:link>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </s:layout-component>

    </s:layout-component>
</s:layout-render>
