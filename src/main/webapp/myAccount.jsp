<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="credentials.change_title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.AccountActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">

        <table border="1">
            <tr>
                <th><f:message key="id"/></th>
                <th><f:message key="name"/></th>
                <th><f:message key="surname"/></th>
                <th><f:message key="online"/></th>
            </tr>
            <tr>
                <td><c:out value="${ab.loggedResearcher.id}"/></td>
                <td><c:out value="${ab.loggedResearcher.name}"/></td>
                <td><c:out value="${ab.loggedResearcher.surname}"/></td>
                <td><c:out value="${ab.loggedResearcher.online}"/></td>
            </tr>
        </table>

        <s:form beanclass="bbmri.action.AccountActionBean">
            <s:hidden name="researcher.id"/>
            <s:hidden name="researcher.password"/>
            <fieldset>
                <legend><f:message key="credentials.change_credentials"/></legend>
                <%@include file="/form/createResearcherForm.jsp" %>
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