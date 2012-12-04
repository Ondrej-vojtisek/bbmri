<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fn" uri="/WEB-INF/custom-functions.tld"  %>


<f:message key="biobanks.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Biobank.AllBiobanksActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="body">


        <fieldset>
            <legend><f:message key="biobanks.listOfBanks"/></legend>
            <table border="1">
                <tr>
                    <th><s:label name="biobank.id"/></th>
                    <th><s:label name="biobank.name"/></th>
                    <th><s:label name="biobank.address"/></th>
                    <th><s:label name="biobank.operator"/></th>
                    <th><s:label name="biobank.ethicalCommittee"/></th>
                </tr>
                <c:forEach items="${ab.biobanks}" var="biobank">
                    <tr>
                        <td><c:out value="${biobank.id}"/></td>
                        <td><c:out value="${biobank.name}"/></td>
                        <td><c:out value="${biobank.address}"/></td>
                        <td><c:out value="${biobank.administrator}"/></td>
                        <td><c:out value="${biobank.ethicalCommittee}"/></td>
                        <td>
                                <c:if test="${fn:isAdmin(biobank.administrator, ab.loggedUser)}">
                                     <s:link beanclass="bbmri.action.Biobank.AllBiobanksActionBean" event="edit">
                                     <s:param name="biobank.id" value="${biobank.id}"/><f:message key="edit"/></s:link>

                                </c:if>
                        </td>

                    </tr>
                </c:forEach>
            </table>
        </fieldset>

    </s:layout-component>
</s:layout-render>