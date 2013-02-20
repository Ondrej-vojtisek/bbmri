<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="credentials.change_title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.AccountActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="primary_menu">
        <li><s:link href="/project_all.jsp"><f:message key="projects"/></s:link></li>
        <li><s:link href="/biobank_all.jsp"><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp"><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp"><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
    </s:layout-component>
    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.AccountActionBean">
            <s:hidden name="user.id"/>
            <s:hidden name="user.password"/>
            <fieldset>
                <legend><f:message key="credentials.change_credentials"/></legend>
                <%@include file="/form/createUserForm.jsp" %>
                <s:submit name="update"><f:message key="save"/></s:submit>
            </fieldset>
        </s:form>

        <s:form beanclass="bbmri.action.AccountActionBean">
            <fieldset>
                <legend><f:message key="credentials.change_password"/></legend>
                <%@include file="/form/changePasswordForm.jsp" %>
                <s:submit name="changePassword"><f:message key="save"/></s:submit>
            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>