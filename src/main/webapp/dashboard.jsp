<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.WelcomeActionBean.welcome" var="title"/>

<s:useActionBean var="actionBean" beanclass="cz.bbmri.action.DashboardActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp"
                 title="${title}" primarymenu="home">

    <s:layout-component name="body">


        <%--<div id="my-timeline"></div>--%>

        <s:form beanclass="cz.bbmri.action.DashboardActionBean">

            <c:if test="${empty actionBean.notifications}">

                <f:message key="cz.bbmri.action.DashboardActionBean.noNewNotifications"/>

            </c:if>

            <c:forEach items="${actionBean.notifications}" var="notification">

                <table class="table table-bordered notification navbar-inverse">
                    <tr class="firstLine" style="color: white;">
                        <td>
                            <s:checkbox name="selectedNotifications" value="${notification.id}"/>
                        </td>
                        <td>
                            <s:layout-render name="/webpages/component/detail/date/date.jsp" date="${notification.created}"/>
                        </td>
                        <td></td>
                        <td>
                            <c:if test="${not notification.read}">
                                <f:message key="new"/>
                            </c:if>
                        </td>

                    </tr>

                    <tr>
                        <td style="width: 60px;"></td>
                        <td colspan="2">${notification.message}</td>
                        <td class="action" style="width: 60px;">
                            <c:if test="${not empty notification.notificationType.actionBeanName
                                                and not empty notification.notificationType.confirmEvent}">

                                <s:link beanclass="${notification.notificationType.actionBeanName}"
                                        event="${notification.notificationType.confirmEvent}" class="btn btn-info">
                                        <s:param name="${notification.notificationType.parameter}"
                                                 value="${notification.objectId}"/>
                                    <f:message key="detail"/>
                                </s:link>

                            </c:if>
                        </td>
                    </tr>

                </table>

            </c:forEach>

            <div class="form-actions">

                <s:submit name="deleteSelected" class="btn btn-primary btnMargin"/>
                <s:submit name="markSelectedAsRead" class="btn btn-primary btnMargin"/>
                <%--<s:submit name="deleteAll" class="btn btn-primary btnMargin"/>--%>
                <%--<s:submit name="markAsReadAll" class="btn btn-primary"/>--%>

            </div>

        </s:form>


    </s:layout-component>

</s:layout-render>

