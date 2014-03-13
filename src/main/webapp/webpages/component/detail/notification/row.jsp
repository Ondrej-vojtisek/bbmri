<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <table class="table table-bordered notification navbar-inverse">
        <tr class="firstLine" style="color: white;">
            <td>
                <s:checkbox name="selectedNotifications" value="${record.id}"/>
            </td>
            <td>
                <s:layout-render name="/webpages/component/detail/date/date.jsp" date="${record.created}"/>
            </td>
            <td></td>
            <td>
                <c:if test="${not record.read}">
                    <f:message key="new"/>
                </c:if>
            </td>

        </tr>

        <tr>
            <td style="width: 60px;"></td>
            <td colspan="2">${record.message}</td>
            <td class="action" style="width: 60px;">
                <c:if test="${not empty record.notificationType.actionBeanName
                                                    and not empty record.notificationType.confirmEvent}">

                    <s:link beanclass="${record.notificationType.actionBeanName}"
                            event="${record.notificationType.confirmEvent}" class="btn btn-info">
                        <s:param name="${record.notificationType.parameter}"
                                 value="${record.objectId}"/>
                        <f:message key="detail"/>
                    </s:link>

                </c:if>
            </td>
        </tr>

    </table>

</s:layout-definition>