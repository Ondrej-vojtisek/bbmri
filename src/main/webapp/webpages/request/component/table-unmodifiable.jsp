<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<h2><format:message key="cz.bbmri.entity.Request.required"/></h2>
<table class="table table-hover table-striped">
    <stripes:layout-render name="${component.header.request}"/>


    <%--Initialization of generalized typ requisition which stand for any type which is used to --%>
    <%--make sample allocation--%>

    <core:if test="${not empty actionBean.reservation}">
        <core:set var="requisition" value="${actionBean.reservation}"/>
    </core:if>

    <core:if test="${not empty actionBean.question}">
        <core:set var="requisition" value="${actionBean.question}"/>
    </core:if>

    <core:if test="${not empty actionBean.withdraw}">
        <core:set var="requisition" value="${actionBean.withdraw}"/>
    </core:if>


    <tbody>
    <stripes:layout-render name="${component.table.emptyTable}"
                           collection="${requisition.request}"/>
    <core:forEach var="item" items="${requisition.request}">

        <tr>
            <stripes:layout-render name="${component.row.request}" item="${item}"/>
        </tr>
    </core:forEach>
    </tbody>
</table>