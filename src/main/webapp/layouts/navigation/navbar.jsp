<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="bean" beanclass="cz.bbmri.action.base.BasicActionBean"/>

<%-- -------------------------------------------------------------------- --%>
<c:if test="${not empty bean.context.myId}">

    <c:set var="logged" scope="session" value="${bean.loggedUser.wholeName}"/>

    <p class="navbar-text pull-right">
        <s:link event="logout" beanclass="cz.bbmri.action.LoginActionBean">
            <f:message key="logout"/></s:link>
    </p>


    <%-- -------------------------------------------------------------------- --%>


    <p class="navbar-text pull-right" style="margin-right: 30px;">
        <f:message key="logged_user"/>:
        <s:link beanclass="cz.bbmri.action.user.UserActionBean" event="detail">
            <s:param name="id" value="${bean.context.myId}"/>
            <c:out value="${logged}"/>
        </s:link>
    </p>

</c:if>
<%-- -------------------------------------------------------------------- --%>

<ul class="navbar-text pull-right dropdown" style="margin-right: 30px;">
    <a href="#" class="navbar-text dropdown-toggle" data-toggle="dropdown">
        ${bean.context.locale}
    </a>
    <ul class="dropdown-menu">

        <%-- -------------------------------------------------------------------- --%>

        <li <c:if test="${bean.context.locale eq 'en'}"> class="active" </c:if>>
            <s:link href="${bean.lastUrl}">
                <s:param name="locale" value="en"/>
                <f:message key="navbar.english"/>
            </s:link>
        </li>

        <%-- -------------------------------------------------------------------- --%>

        <li <c:if test="${bean.context.locale eq 'cs'}"> class="active" </c:if>>
            <s:link href="${bean.lastUrl}">
                <s:param name="locale" value="cs"/>
                <f:message key="navbar.czech"/>
            </s:link>
        </li>

        <%-- -------------------------------------------------------------------- --%>

    </ul>
</ul>

<%-- -------------------------------------------------------------------- --%>

<p class="navbar-text pull-right">
    <b><f:message key="version"/>:</b> <i>PROJECT_VERSION</i>
</p>


<%-- -------------------------------------------------------------------- --%>

