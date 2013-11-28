<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="bean" beanclass="bbmri.action.BasicActionBean"/>
<c:set var="logged" scope="session" value="${bean.loggedUser.wholeName}"/>

<p class="navbar-text pull-right">
    <s:link event="logout" beanclass="bbmri.action.LoginActionBean">
        <f:message key="logout"/></s:link>
</p>

<p class="navbar-text pull-right" style="margin-right: 30px;">
    <f:message key="logged_user"/>:
    <s:link beanclass="bbmri.action.user.UserActionBean" event="detail">
        <s:param name="id" value="${bean.context.myId}"/>
        <c:out value="${logged}"/>
    </s:link>
</p>

<p class="navbar-text pull-right" style="margin-right: 30px;">
    <b><f:message key="version"/>:</b> <i>PROJECT_VERSION</i>
</p>