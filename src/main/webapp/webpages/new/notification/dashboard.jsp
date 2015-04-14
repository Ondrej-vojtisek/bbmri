<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.entity.Notification.notifications" var="title"/>

<stripes:layout-render name="${component.layout.content}"
                       title="${title}" primarymenu="home">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.DashboardActionBean">

            <core:if test="${empty actionBean.pagination.myPageList}">

                <format:message key="cz.bbmri.action.DashboardActionBean.noNewNotifications"/>

            </core:if>

            <core:forEach items="${actionBean.pagination.myPageList}" var="item">

                <stripes:layout-render name="${component.row.notification}" item="${item}"/>

            </core:forEach>

            <core:if test="${not empty actionBean.pagination.myPageList}">

                <stripes:layout-render name="${component.pager}"
                                       pagination="${actionBean.pagination}"/>

            </core:if>

            <div class="form-actions">
                <stripes:submit name="deleteSelected" class="btn btn-primary btnMargin"/>
                <stripes:submit name="markSelectedAsRead" class="btn btn-primary btnMargin"/>
            </div>

        </stripes:form>



    </stripes:layout-component>


</stripes:layout-render>
