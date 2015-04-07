<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%--<format:message key="cz.bbmri.action.project.ProjectActionBean.createSampleRequest" var="title"/>--%>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="sampleRequests">

    <stripes:layout-component name="body">

        <stripes:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                         pagination="${actionBean.pagination}"
                         componentManager="${actionBean.componentManager}"
                         targetBean="cz.bbmri.action.request.RequestActionBean"
                         eventName="detail"
                         paramName="sampleQuestionId"/>


        <%--<table class="table table-hover table-striped">--%>

        <%--<stripes:layout-render name="/webpages/component/detail/sampleQuestion/header.jsp"/>--%>

        <%--<tbody>--%>

        <%--<stripes:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"--%>
        <%--collection="${actionBean.project.sampleRequests}"/>--%>

        <%--<core:forEach items="${actionBean.project.sampleRequests}" var="sampleRequest">--%>
        <%--<tr>--%>
        <%--<stripes:layout-render name="/webpages/component/detail/sampleQuestion/row.jsp"--%>
        <%--record="${sampleRequest}"/>--%>

        <%--<td class="action">--%>
        <%--<stripes:link beanclass="cz.bbmri.action.request.RequestActionBean" event="detail"--%>
        <%--class="btn btn-primary">--%>
        <%--<stripes:param name="sampleQuestionId" value="${sampleRequest.id}"/>--%>
        <%--<stripes:param name="biobankId" value="${sampleRequest.biobank.id}"/>--%>
        <%--<format:message key="detail"/>--%>
        <%--</stripes:link>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--</core:forEach>--%>
        <%--</tbody>--%>
        <%--</table>--%>
    </stripes:layout-component>
</stripes:layout-render>
