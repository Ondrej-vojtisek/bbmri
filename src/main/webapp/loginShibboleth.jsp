<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="signin" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.LoginShibbolethActionBean"/>
<s:layout-render name="/layouts/layout_login.jsp" title="${title}">
    <s:layout-component name="body">

            ${ab.context.shibbolethSession}
            </br>
            ${ab.context.shibbolethDisplayName}

    </s:layout-component>
</s:layout-render>

