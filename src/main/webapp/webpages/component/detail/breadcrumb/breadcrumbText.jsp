<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <%--MsgKey will be used to find localized String --%>
    <c:if test="${not item.notMsgKey}">

        <f:message key="${item.msgKey}"/>

    </c:if>

    <%--MsgKey is ordinary String and it won't be used for localization --%>
    <c:if test="${item.notMsgKey}">

        ${item.msgKey}

    </c:if>

</s:layout-definition>