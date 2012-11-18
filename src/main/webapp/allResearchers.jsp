<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="researchers.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.ResearcherActionBean"/>


<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">

        <table border="1">
            <tr>
                <th><f:message key="id"/></th>
                <th><f:message key="name"/></th>
                <th><f:message key="surname"/></th>
            </tr>
            <c:forEach var="researcher" items="${ab.researchers}">
                <tr>
                    <td><c:out value="${researcher.id}"/></td>
                    <td><c:out value="${researcher.name}"/></td>
                    <td><c:out value="${researcher.surname}"/></td>
                </tr>
            </c:forEach>
        </table>
    </s:layout-component>
</s:layout-render>