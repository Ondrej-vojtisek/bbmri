<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="users.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.UserActionBean"/>


<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="primary_menu">
        <li><s:link href="/project_all.jsp"><f:message key="projects"/></s:link></li>
        <li><s:link href="/biobank_all.jsp"><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li class="active"><s:link href="/user_all.jsp"><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp"><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
        <c:if test="${ab.loggedUser.administrator}">
            <li class="active"><s:link href="/user_all.jsp"><f:message key="all"/></s:link></li>
            <li><s:link href="/user_create.jsp"><f:message key="user.create"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="body">

        <table border="1">
            <tr>
                <th><s:label name="id"/></th>
                <th><s:label name="name"/></th>
                <th><s:label name="surname"/></th>
            </tr>
            <c:forEach var="user" items="${ab.users}">
                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.surname}"/></td>
                </tr>
            </c:forEach>
        </table>
    </s:layout-component>
</s:layout-render>