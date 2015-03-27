<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<s:layout-render name="${component.layout.content}"
                 primarymenu="user">

    <s:layout-component name="body">

        <s:layout-render name="${component.menu.user}" active="settings"/>

    </s:layout-component>
</s:layout-render>