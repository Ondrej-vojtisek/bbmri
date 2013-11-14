<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.user.UserActionBean.findUser" var="title"/>
<s:useActionBean var="userFindBean" beanclass="bbmri.action.user.FindUserActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="user"
                 secondarymenu="user_find">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/component/findUser.jsp"
                         context="user"
                         findBean="${userFindBean}"/>

    </s:layout-component>
</s:layout-render>