<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <%--if breadcrumbs are empty than don't print anything--%>
    <c:if test="${not empty actionBean.breadcrumbs}">

        <ul class="breadcrumb">

            <c:forEach items="${actionBean.breadcrumbs}" var="item">
                <li>
                        <%--active -> current page -> no need to create link--%>
                    <c:if test="${item.active}">

                        <%--text of breadcumb--%>
                        <s:layout-render name="/webpages/component/detail/breadcrumb/breadcrumbText.jsp"
                                         item="${item}"/>

                    </c:if>

                        <%-- not active -> it is not current page and it should be link--%>
                    <c:if test="${not item.active}">

                        <s:link beanclass="${item.actionBean}"
                                event="${item.event}">

                            <%--set param--%>
                            <c:if test="${not empty item.param}">
                                <s:param name="${item.param}" value="${item.paramValue}"/>
                            </c:if>

                            <%--text of breadcumb--%>
                            <s:layout-render name="/webpages/component/detail/breadcrumb/breadcrumbText.jsp"
                                             item="${item}"/>

                        </s:link>
                    </c:if>

                    <%--Divider between breadcrumbs--%>
                    <span class="divider">></span>
                </li>
            </c:forEach>
        </ul>

    </c:if>


</s:layout-definition>