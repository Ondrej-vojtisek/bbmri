<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="credentials.change_title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.user.AccountActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="account"
                 ternarymenu="personal_data">

    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.user.AccountActionBean">
            <s:hidden name="user.id"/>
            <s:hidden name="user.password"/>
            <fieldset>
                <legend><f:message key="credentials.change_credentials"/></legend>
                <span><s:label name="bbmri.entities.User.lastLogin"/>: ${ab.user.lastLogin}</span>
                <table>
                    <tr>
                        <th><s:label name="user.name"/></th>
                        <td><s:text name="user.name"/></td>
                    </tr>
                    <tr>
                        <th><s:label name="user.surname"/></th>
                        <td><s:text name="user.surname"/></td>
                    </tr>

                    <tr>
                        <th><s:label name="bbmri.entities.User.created"/></th>
                        <td>${ab.user.created}</td>
                    </tr>
                </table>
                <s:submit name="update"><f:message key="save"/></s:submit>
            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>