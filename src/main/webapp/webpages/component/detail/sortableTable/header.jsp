<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <%--sort event--%>
    <s:link beanclass="${actionBean.name}" event="${pagination.event}">

        <%--which column is used to order--%>
        <c:if test="${pagination.orderParam eq column}">

            <%--visibility of arrows--%>
            <s:layout-render name="/webpages/component/detail/sortableTable/arrows.jsp"
                             pagination="${pagination}"/>

            <%--switch desc to asc--%>
            <s:param name="desc${pagination.webParamDiscriminator}" value="${not pagination.desc}"/>
        </c:if>

        <%--param defining by which column is table ordered--%>
        <s:param name="orderParam${pagination.webParamDiscriminator}" value="${column}"/>

        <%-- we must store param about context - for instance for which biobank we print samples--%>
        <c:if test="${not empty pagination.identifierParam}">

            <s:param name="${pagination.identifierParam}"
                     value="${pagination.identifier}"/>

        </c:if>

        <%--header text--%>
        <f:message key="${msgKey}"/>
    </s:link>

</s:layout-definition>