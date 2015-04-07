<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <core:if test="${empty collection}">
        <tr>
            <td colspan="100%">
                <format:message key="empty"/>
            </td>
        </tr>
    </core:if>

</stripes:layout-definition>
