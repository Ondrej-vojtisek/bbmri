<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <table class="table table-bordered notification navbar-inverse">
        <tr class="firstLine" style="color: white;">
            <td>
                <s:checkbox name="selectedNotifications" value="${item.id}"/>
            </td>
            <td>
                <s:layout-render name="${component.date}" date="${item.created}"/>
            </td>
            <td></td>
            <td>
                <c:if test="${not item.read}">
                    <f:message key="new"/>
                </c:if>
            </td>

        </tr>

        <tr>
            <td style="width: 60px;"></td>
            <td colspan="2">${item.message}</td>
            <td class="action" style="width: 60px;">
                <c:if test="${not empty item.notificationType.actionBeanName
                                                    and not empty item.notificationType.eventName}">

                    <s:link beanclass="${item.notificationType.actionBeanName}"
                            event="${item.notificationType.eventName}" class="btn btn-info">
                        <s:param name="${item.notificationType.parameter}"
                                 value="${item.objectId}"/>
                        <f:message key="detail"/>
                    </s:link>

                </c:if>
            </td>
        </tr>

    </table>

</s:layout-definition>