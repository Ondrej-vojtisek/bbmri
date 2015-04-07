<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <table class="table table-bordered notification navbar-inverse">
        <tr class="firstLine" style="color: white;">
            <td>
                <stripes:checkbox name="selectedNotifications" value="${item.id}"/>
            </td>
            <td>
                <fmt:formatDate value="${item.created}" type="both"/>
            </td>
            <td></td>
            <td>
                <core:if test="${not item.read}">
                    <format:message key="new"/>
                </core:if>
            </td>

        </tr>

        <tr>
            <td style="width: 60px;"></td>
            <td colspan="2">${item.message}</td>
            <td class="action" style="width: 60px;">
                <core:if test="${not empty item.notificationType.actionBeanName
                                                    and not empty item.notificationType.eventName}">

                    <stripes:link beanclass="${item.notificationType.actionBeanName}"
                            event="${item.notificationType.eventName}" class="btn btn-info">
                        <stripes:param name="${item.notificationType.parameter}"
                                 value="${item.objectId}"/>
                        <format:message key="detail"/>
                    </stripes:link>

                </core:if>
            </td>
        </tr>

    </table>

</stripes:layout-definition>