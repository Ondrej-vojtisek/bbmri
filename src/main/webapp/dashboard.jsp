<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.entities.Notification.notifications" var="title"/>

<s:layout-render name="/layouts/layout_content.jsp"
                 title="${title}" primarymenu="home">

    <s:layout-component name="body">

        <div id="my-timeline"></div>

        <s:form beanclass="cz.bbmri.action.DashboardActionBean">

            <c:if test="${empty actionBean.pagination.myPageList}">

                <f:message key="cz.bbmri.action.DashboardActionBean.noNewNotifications"/>

            </c:if>

            <c:forEach items="${actionBean.pagination.myPageList}" var="item">

                <s:layout-render name="${actionBean.componentManager.tableRow}" record="${item}"/>

            </c:forEach>

            <c:if test="${not empty actionBean.pagination.myPageList}">

                <s:layout-render name="/webpages/component/detail/sortableTable/pagination.jsp"
                                 pagination="${actionBean.pagination}"/>

            </c:if>

            <div class="form-actions">
                <s:submit name="deleteSelected" class="btn btn-primary btnMargin"/>
                <s:submit name="markSelectedAsRead" class="btn btn-primary btnMargin"/>
            </div>

        </s:form>


    </s:layout-component>

</s:layout-render>

