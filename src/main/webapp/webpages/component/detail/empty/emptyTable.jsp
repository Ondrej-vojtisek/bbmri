<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <c:if test="${empty collection}">
        <tr>
            <td colspan="${colspan}">
                <f:message key="empty"/>
            </td>
        </tr>
    </c:if>

</s:layout-definition>
