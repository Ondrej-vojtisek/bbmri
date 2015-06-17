<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="userActionBean" beanclass="cz.bbmri.action.UserActionBean"/>

<stripes:layout-definition>
    <%-- -------------------------------------------------------------------- --%>
    <core:if test="${not empty actionBean.context.myId}">

        <core:set var="logged" scope="session" value="${actionBean.loggedUser.wholeName}"/>

        <p class="navbar-text pull-right">
            <stripes:link event="logout" beanclass="cz.bbmri.action.LogoutActionBean">
                <format:message key="logout"/>
            </stripes:link>
        </p>


        <%-- -------------------------------------------------------------------- --%>


        <p class="navbar-text pull-right" style="margin-right: 30px;">
            <format:message key="logged_user"/>:
            <security:allowed bean="userActionBean">
                <stripes:link beanclass="cz.bbmri.action.UserActionBean" event="detail">
                    <stripes:param name="id" value="${actionBean.context.myId}"/>
                    ${logged}
                </stripes:link>
            </security:allowed>

            <security:notAllowed bean="userActionBean">
                ${logged}
            </security:notAllowed>
        </p>

    </core:if>
    <%-- -------------------------------------------------------------------- --%>

    <ul class="navbar-text pull-right dropdown" style="margin-right: 30px;">
        <a href="#" class="navbar-text dropdown-toggle" data-toggle="dropdown">
                ${actionBean.context.locale}
        </a>
        <ul class="dropdown-menu">

                <%-- -------------------------------------------------------------------- --%>

            <li <core:if test="${actionBean.context.locale eq 'en'}"> class="active" </core:if>>
                <stripes:link href="${actionBean.lastUrl}">
                    <stripes:param name="locale" value="en"/>
                    <format:message key="navbar.english"/>
                </stripes:link>
            </li>

                <%-- -------------------------------------------------------------------- --%>

            <li <core:if test="${actionBean.context.locale eq 'cs'}"> class="active" </core:if>>
                <stripes:link href="${actionBean.lastUrl}">
                    <stripes:param name="locale" value="cs"/>
                    <format:message key="navbar.czech"/>
                </stripes:link>
            </li>

        </ul>
    </ul>

    <%-- -------------------------------------------------------------------- --%>

    <p class="navbar-text pull-right">
        <b><format:message key="version"/>:</b> <i>2.0 (8. 6. 2015)</i>
    </p>

    <%-- -------------------------------------------------------------------- --%>

</stripes:layout-definition>
