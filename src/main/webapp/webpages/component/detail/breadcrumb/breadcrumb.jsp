<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <%--if breadcrumbs are empty than don't print anything--%>
    <core:if test="${not empty actionBean.breadcrumbs}">

        <ul class="breadcrumb">

            <core:forEach items="${actionBean.breadcrumbs}" var="item">
                <li>
                        <%--active -> current page -> no need to create link--%>
                    <core:if test="${item.active}">

                        <%--text of breadcumb--%>
                        <stripes:layout-render name="/webpages/component/detail/breadcrumb/breadcrumbText.jsp"
                                         item="${item}"/>

                    </core:if>

                        <%-- not active -> it is not current page and it should be link--%>
                    <core:if test="${not item.active}">

                        <stripes:link beanclass="${item.actionBean}"
                                event="${item.event}">

                            <%--set param--%>
                            <core:if test="${not empty item.param}">
                                <stripes:param name="${item.param}" value="${item.paramValue}"/>
                            </core:if>

                            <%--text of breadcumb--%>
                            <stripes:layout-render name="/webpages/component/detail/breadcrumb/breadcrumbText.jsp"
                                             item="${item}"/>

                        </stripes:link>
                    </core:if>

                    <%--Divider between breadcrumbs--%>
                    <span class="divider">/</span>
                </li>
            </core:forEach>
        </ul>

    </core:if>


</stripes:layout-definition>