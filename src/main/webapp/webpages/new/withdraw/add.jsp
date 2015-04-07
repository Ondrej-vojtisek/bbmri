<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.entity.Withdraw.add" var="title"/>

<stripes:layout-render name="${component.layout.content}" title="${title}"
                       primarymenu="biobank">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.biobank}" active="withdraws"/>

    </stripes:layout-component>
</stripes:layout-render>
