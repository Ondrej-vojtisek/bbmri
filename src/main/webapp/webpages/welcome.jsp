<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.WelcomeActionBean.welcome" var="title"/>

<s:useActionBean var="actionBean" beanclass="bbmri.action.WelcomeActionBean"/>
<%--<s:layout-render name="/layouts/layout_content.jsp"--%>
<%--title="${title}">--%>

<%--<s:layout-component name="body">--%>


<%--</s:layout-component>--%>

<%--</s:layout-render>--%>

<c:if test="${actionBean.context.isShibbolethSession}">

    Shibboleth session

    <%--${actionBean.user}--%>

</c:if>

<c:if test="${not actionBean.context.isShibbolethSession}">


    not Shibboleth session
    <s:link beanclass="bbmri.action.LoginActionBean"
            class="btn btn-primary">
        <f:message key="Sign in"/>
    </s:link>
</c:if>


WELCOME