<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>
        <s:layout-render name="/webpages/component/detail/date/date.jsp" date="${record.created}"/>
    </td>
    <td>${record.actor}</td>
    <td>${record.message}</td>

</s:layout-definition>

