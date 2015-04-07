<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="${component.layout.content}"
                 primarymenu="user">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.user}" active="settings"/>

    </stripes:layout-component>
</stripes:layout-render>