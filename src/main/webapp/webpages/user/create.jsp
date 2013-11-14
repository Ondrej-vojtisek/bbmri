<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="bbmri.action.user.UserActionBean.create" var="title"/>
<s:useActionBean var="userBean" beanclass="bbmri.action.user.UserActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="user"
                 secondarymenu="user_create">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.user.UserActionBean">
                <%@include file="/form/createUserAndPasswordForm.jsp" %>
                <s:submit name="create" class="btn btn-primary"/>
        </s:form>
    </s:layout-component>
</s:layout-render>