<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.user.UserActionBean.all" var="title"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="user"
                 secondarymenu="user_all">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                eventName="detail" paramName="userId"/>

    </s:layout-component>

</s:layout-render>