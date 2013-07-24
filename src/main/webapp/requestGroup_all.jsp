<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="sample.requests" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleQuestionActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.wholeName}"
                 primarymenu="biobank"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}"
                 secondarymenu="requestGroup_all">

    <s:layout-component name="body">


        <table id="sortable" cellspacing="0" class="tablesorter">
            <thead>
            <tr>
                <th><s:label name="id"/></th>
                <th><s:label name="requestGroup.created"/></th>
                <th><s:label name="requestGroup.lastModification"/></th>
                <th><s:label name="requestGroup.requestState"/></th>
                <th><s:label name="biobank.name"/></th>
                <th><s:label name="project.name"/></th>
                <th colspan="2" class="noSort"><s:label name="actions"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${ab.allRequestGroups}" var="requestGroup">
                <tr>
                    <td><c:out value="${requestGroup.id}"/></td>
                    <td><c:out value="${requestGroup.created}"/></td>
                    <td><c:out value="${requestGroup.lastModification}"/></td>
                    <td><f:message key="RequestState.${requestGroup.requestState}"/></td>
                    <td><c:out value="${requestGroup.biobank.name}"/></td>
                    <td><c:out value="${requestGroup.project.name}"/></td>
                    <td><s:link beanclass="bbmri.action.SampleQuestionActionBean" event="requestGroupDetail">
                        <s:param name="requestGroup.id" value="${requestGroup.id}"/>
                        <f:message key="detail"/></s:link>
                    </td>
                    <td><s:link beanclass="bbmri.action.SampleQuestionActionBean" event="releaseSamples">
                        <s:param name="requestGroup.id" value="${requestGroup.id}"/>
                        <f:message key="release_samples"/></s:link>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </s:layout-component>
</s:layout-render>