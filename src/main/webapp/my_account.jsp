<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="credentials.change_title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.AccountActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.wholeName}"
                 biobank="${null}"
                 administrator="${ab.loggedUser.administrator}">

    <s:layout-component name="body">
        <fieldset>
            <legend><f:message key="credentials.myRoles"/></legend>
            <table cellspacing="0" class="tablesorter">
                <thead>
                <tr>
                    <th><s:label name="role"/></th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty ab.myRoles}">
                    <tr>
                        <td><f:message key="empty"/></td>
                    </tr>
                </c:if>
                <c:forEach items="${ab.myRoles}" var="role">
                    <tr>
                        <td><c:out value="${role}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </fieldset>


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