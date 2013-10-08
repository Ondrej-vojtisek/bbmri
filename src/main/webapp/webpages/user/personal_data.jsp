<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="credentials.change_title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.user.UserActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="user"
                 secondarymenu="user_all"
                 ternarymenu="personal_data">
    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.user.UserActionBean"  >
            <s:hidden name="user.id"/>
            <s:hidden name="user.password"/>
            <fieldset>
                <legend><f:message key="credentials.change_credentials"/></legend>
                <table>
                    <tr>
                        <th><s:label for="z2" name="user.name"/></th>
                        <td><s:text id="z2" name="user.name" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z3" name="user.surname"/></th>
                        <td><s:text id="z3" name="user.surname" readonly="true"/></td>
                    </tr>
                </table>
            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>