<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="user.create" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.UserActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}"
                 primarymenu="users"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}"
                 secondarymenu="user_create">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.UserActionBean">
            <fieldset>
                <legend><f:message key="user.create"/></legend>
                <%@include file="/form/createUserAndPasswordForm.jsp" %>
                <s:submit name="create"><f:message key="add"/></s:submit>
            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>