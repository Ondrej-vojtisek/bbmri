<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="${component.layout.content}">

    <s:layout-component name="body">

        <s:layout-render name="${component.menu.project}" active="detail"/>

        <s:layout-render name="/webpages/new/project/component/hasMta.jsp"/>

    </s:layout-component>
</s:layout-render>