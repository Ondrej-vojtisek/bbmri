<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<f:message key="sample.requests" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleQuestionActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="primary_menu">
        <li><s:link href="/project_my_projects.jsp"><f:message key="projects"/></s:link></li>
        <li><s:link href="/biobank_all.jsp"><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp"><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp"><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
        <c:if test="${ab.loggedUser.biobank != null}">
            <li><s:link href="/biobank_all.jsp"><f:message key="biobanks_all"/></s:link></li>
            <c:if test="${ab.loggedUser.administrator}">
                <li><s:link href="/biobank_create.jsp"><f:message key="biobank_create"/></s:link></li>
            </c:if>
            <c:if test="${ab.loggedUser.biobank != null}">
                <li><s:link href="/sample_approve_request.jsp"><f:message key="approve_sample_request"/></s:link></li>
                <li><s:link href="/sample_create.jsp"><f:message key="sample_create"/></s:link></li>
                <li><s:link href="/sample_amortize.jsp"><f:message key="sample.amortize"/></s:link></li>
                <li class="active"><s:link href="/requestGroup_all.jsp"><f:message key="sample.requests"/></s:link></li>
            </c:if>
        </c:if>
    </s:layout-component>

    <s:layout-component name="body">


        <table id="sortableTable">
            <thead>
            <tr>
                <th><f:message key="id"/></th>
                <th><f:message key="requestGroup.created"/></th>
                <th><f:message key="requestGroup.lastModification"/></th>
                <th><f:message key="requestGroup.requestState"/></th>
                <th><f:message key="biobank.name"/></th>
                <th><f:message key="project.name"/></th>
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