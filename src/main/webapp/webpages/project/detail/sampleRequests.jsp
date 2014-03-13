<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%--<f:message key="cz.bbmri.action.project.ProjectActionBean.createSampleRequest" var="title"/>--%>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="sampleRequests">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                         pagination="${actionBean.pagination}"
                         componentManager="${actionBean.componentManager}"
                         targetBean="cz.bbmri.action.request.RequestActionBean"
                         eventName="detail"
                         paramName="sampleQuestionId"/>


        <%--<table class="table table-hover table-striped">--%>

        <%--<s:layout-render name="/webpages/component/detail/sampleQuestion/header.jsp"/>--%>

        <%--<tbody>--%>

        <%--<s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"--%>
        <%--collection="${actionBean.project.sampleRequests}"/>--%>

        <%--<c:forEach items="${actionBean.project.sampleRequests}" var="sampleRequest">--%>
        <%--<tr>--%>
        <%--<s:layout-render name="/webpages/component/detail/sampleQuestion/row.jsp"--%>
        <%--record="${sampleRequest}"/>--%>

        <%--<td class="action">--%>
        <%--<s:link beanclass="cz.bbmri.action.request.RequestActionBean" event="detail"--%>
        <%--class="btn btn-primary">--%>
        <%--<s:param name="sampleQuestionId" value="${sampleRequest.id}"/>--%>
        <%--<s:param name="biobankId" value="${sampleRequest.biobank.id}"/>--%>
        <%--<f:message key="detail"/>--%>
        <%--</s:link>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--</c:forEach>--%>
        <%--</tbody>--%>
        <%--</table>--%>
    </s:layout-component>
</s:layout-render>
