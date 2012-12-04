<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="user.create" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.UserActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedUser.name}">
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