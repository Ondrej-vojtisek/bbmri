<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="users.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.UserActionBean"/>

<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}" primarymenu="users"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}"
                 secondarymenu="user_all">

    <s:layout-component name="body">
        <fieldset>
            <legend><f:message key="all_users"/></legend>
            <table id="sortableTable">
                <thead>
                <tr>
                    <th><s:label name="id"/></th>
                    <th><s:label name="name"/></th>
                    <th><s:label name="surname"/></th>
                </tr>
                </thead>
                <tbody>
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