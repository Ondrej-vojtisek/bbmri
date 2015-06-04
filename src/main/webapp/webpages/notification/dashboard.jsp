<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.entity.Notification.notifications" var="title"/>

<stripes:layout-render name="${component.layout.content}"
                       title="${title}" primarymenu="home">

    <stripes:layout-component name="body">

        <%--<div class="alert alert-info" role="alert">--%>
            <%--<h2>Novinky</h2>--%>
            <%--<h3>Známé chyby:</h3>--%>
            <%--<ul>--%>
                <%--<li>V importu z MOÚ je špatný počet alikvotů - chyba je na straně nemocničního systému.</li>--%>
            <%--</ul>--%>

        <%--</div>--%>

        <stripes:form beanclass="cz.bbmri.action.DashboardActionBean">

            <core:if test="${empty actionBean.pagination.myPageList}">

                <format:message key="cz.bbmri.action.DashboardActionBean.noNewNotifications"/>

            </core:if>

            <core:forEach items="${actionBean.pagination.myPageList}" var="item">

                <stripes:layout-render name="${actionBean.componentManager.tableRow}" record="${item}"/>

            </core:forEach>

            <core:if test="${not empty actionBean.pagination.myPageList}">

                <stripes:layout-render name="/webpages/component/detail/sortableTable/pagination.jsp"
                                 pagination="${actionBean.pagination}"/>

            </core:if>

            <div class="form-actions">
                <stripes:submit name="deleteSelected" class="btn btn-primary btnMargin"/>
                <stripes:submit name="markSelectedAsRead" class="btn btn-primary btnMargin"/>
            </div>

        </stripes:form>


    </stripes:layout-component>

</stripes:layout-render>
