<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="users.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.user.UserActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}" primarymenu="users"
                 secondarymenu="user_all">

    <s:layout-component name="body">
        <fieldset>
            <legend><f:message key="all_users"/></legend>
            <table id="sortable" cellspacing="0" class="tablesorter">
                <thead>
                <tr>
                    <th><s:label name="id"/></th>
                    <th><s:label name="name"/></th>
                    <th><s:label name="surname"/></th>
                </tr>
                </thead>
                <tbody>

                <c:if test="${empty ab.users}">
                    <tr>
                        <td colspan="3"><f:message key="empty"/></td>
                    </tr>
                </c:if>

                <c:forEach var="user" items="${ab.users}">
                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.name}"/></td>
                        <td><c:out value="${user.surname}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </fieldset>
    </s:layout-component>
</s:layout-render>