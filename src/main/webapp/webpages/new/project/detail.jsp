<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.project}" active="detail"/>

        <stripes:layout-render name="/webpages/new/project/component/hasMta.jsp"/>

    </stripes:layout-component>
</stripes:layout-render>