<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<s:useActionBean var="ab" beanclass="bbmri.action.ProjectActionBean"/>
<s:layout-render name="/model/design.jsp" title="Projekty" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">
        <table border="1">
            <p>All projects</p>
            <c:forEach items="${ab.projects}" var="z">
                <tr>
                    <td><c:out value="${z.id}"/></td>
                    <td><c:out value="${z.name}"/></td>
                    <td><c:out value="${z.description}"/></td>
                    <td><c:out value="${z.state}"/></td>
                </tr>
            </c:forEach>
        </table>
    </s:layout-component>
</s:layout-render>