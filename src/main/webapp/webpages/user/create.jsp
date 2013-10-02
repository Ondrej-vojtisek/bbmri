<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="user.create" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.user.UserActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="users"
                 secondarymenu="user_create">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.user.UserActionBean">
            <fieldset>
                <legend><f:message key="user.create"/></legend>
                <%@include file="/form/createUserAndPasswordForm.jsp" %>
                <s:submit name="create"><f:message key="add"/></s:submit>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>