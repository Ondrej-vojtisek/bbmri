<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fn" uri="/WEB-INF/custom-functions.tld" %>


<f:message key="biobanks.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Biobank.AllBiobanksActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="primary_menu">
        <li><s:link href="/project_all.jsp"><f:message key="projects"/></s:link></li>
        <li class="active"><s:link href="/biobank_all.jsp"><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp"><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp"><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
        <li class="active"><s:link href="/biobank_all.jsp"><f:message key="all"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/biobank_create.jsp"><f:message key="biobank_create"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.biobank != null}">
            <li><s:link href="/sample_approve_request.jsp"><f:message key="approve_sample_request"/></s:link></li>
            <li><s:link href="/sample_create.jsp"><f:message key="sample_create"/></s:link></li>
            <li><s:link href="/sample_release.jsp"><f:message key="sample.release"/></s:link></li>
            <li><s:link href="/sample_all.jsp"><f:message key="sample.all"/></s:link></li>
            <li><s:link href="/sample_released.jsp"><f:message key="sample.released"/></s:link></li>
        </c:if>
    </s:layout-component>


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
                    <th><s:label name="actions"/></th>
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