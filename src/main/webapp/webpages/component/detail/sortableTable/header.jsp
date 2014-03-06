<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <%--sort event--%>
    <s:link beanclass="${actionBean.name}" event="display">

        <%--which column is used to order--%>
        <c:if test="${actionBean.orderParam eq column}">

            <%--visibility of arrows--%>
            <s:layout-render name="/webpages/component/detail/sortableTable/arrows.jsp"/>

            <%--switch desc to asc--%>
            <s:param name="desc" value="${not actionBean.desc}"/>
        </c:if>

        <%--param defining by which column is table ordered--%>
        <s:param name="orderParam" value="${column}"/>

        <%--header text--%>
        <f:message key="${msgKey}"/>
    </s:link>


</s:layout-definition>