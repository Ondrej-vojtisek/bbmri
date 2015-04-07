<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <%--MsgKey will be used to find localized String --%>
    <core:if test="${not item.notMsgKey}">

        <format:message key="${item.msgKey}"/>

    </core:if>

    <%--MsgKey is ordinary String and it won't be used for localization --%>
    <core:if test="${item.notMsgKey}">

        ${item.msgKey}

    </core:if>

    <%--For instance to print Rack A instead of only Rack--%>
    <core:if test="${not empty item.objectName}">

         ${" "} ${item.objectName}

     </core:if>

</stripes:layout-definition>