<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <%--sort event--%>
    <stripes:link beanclass="${actionBean.name}" event="${pagination.event}">

        <%--which column is used to order--%>
        <core:if test="${pagination.orderParam eq column}">

            <%--visibility of arrows--%>
            <stripes:layout-render name="/webpages/component/detail/sortableTable/arrows.jsp"
                             pagination="${pagination}"/>

            <%--switch desc to asc--%>
            <stripes:param name="desc${pagination.webParamDiscriminator}" value="${not pagination.desc}"/>
        </core:if>

        <%--param defining by which column is table ordered--%>
        <stripes:param name="orderParam${pagination.webParamDiscriminator}" value="${column}"/>

        <%-- we must store param about context - for instance for which biobank we print samples--%>
        <core:if test="${not empty pagination.identifierParam}">

            <stripes:param name="${pagination.identifierParam}"
                     value="${pagination.identifier}"/>

        </core:if>

        <%--header text--%>
        <format:message key="${msgKey}"/>
    </stripes:link>

</stripes:layout-definition>