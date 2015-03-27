<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="${component.layout.content}"
                 primarymenu="biobank">

    <s:layout-component name="body">

        <s:layout-render name="${component.menu.biobank}" active="biobankuser"/>

        ADD BIOBANK USER

    </s:layout-component>
</s:layout-render>