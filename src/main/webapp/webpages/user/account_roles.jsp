<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="credentials.myRoles" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.user.AccountActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="account"
                 ternarymenu="roles">

    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="credentials.myRoles"/></legend>
            <table cellspacing="0" class="tablesorter">
                <thead>
                <tr>
                    <th><s:label name="role"/></th>
                    <th><s:label name="permission"/></th>
                    <th><s:label name="reference"/></th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty ab.myRoles}">
                    <tr>
                        <td><f:message key="empty"/></td>
                    </tr>
                </c:if>
                <c:forEach items="${ab.myRoles}" var="roleDTO">
                    <tr>
                        <td><c:out value="${roleDTO.subject}"/></td>
                        <td><c:out value="${roleDTO.permission}"/></td>
                        <td><c:out value="${roleDTO.referenceId}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </fieldset>

    </s:layout-component>
</s:layout-render>