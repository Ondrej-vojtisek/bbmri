<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="biobanks.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Biobank.BiobankActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.wholeName}"
                 primarymenu="biobank"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}"
                 secondarymenu="biobank_all">

    <s:layout-component name="body">
        <fieldset>
            <legend><f:message key="biobanks.listOfBanks"/></legend>
            <table cellspacing="0" class="tablesorter">
                <thead>
                <tr>
                    <th><s:label name="biobank.name"/></th>
                    <th><s:label name="biobank.address"/></th>
                    <th class="noSort"><s:label name="actions"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${ab.biobanks}" var="biobank">
                    <tr>
                        <td><c:out value="${biobank.name}"/></td>
                        <td><c:out value="${biobank.address}"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${biobank.id == ab.loggedUser.biobank.id}">
                                    <s:link beanclass="bbmri.action.Biobank.BiobankActionBean" event="edit">
                                        <s:param name="biobank.id" value="${biobank.id}"/><f:message
                                            key="edit"/></s:link>
                                </c:when>
                                <c:otherwise>
                                    <f:message key="edit"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </fieldset>
    </s:layout-component>
</s:layout-render>