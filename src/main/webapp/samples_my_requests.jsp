<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="sample.request" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleQuestionActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="samples_my_requests">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.SampleQuestionActionBean">
            <c:forEach items="${ab.myProjects}" var="project">
                <fieldset>
                    <legend><f:message key="project.name"/>: <c:out value="${project.name}"/></legend>
                    <div><b><f:message key="project.fundingOrganization"/>:</b>
                        <c:out value="${project.fundingOrganization}"/></div>
                    <div><b><f:message key="project.owner"/>:</b>
                        <c:out value="${project.owner.wholeName}"/></div>
                    <div><b><f:message key="project.projectState"/>:</b>
                        <f:message key="ProjectState.${project.projectState}"/></div>
                </fieldset>


                <table id="sortable" cellspacing="0" class="tablesorter">
                    <thead>
                    <tr>
                        <th><s:label name="id"/></th>
                        <th><s:label name="requestGroup.created"/></th>
                        <th><s:label name="requestGroup.lastModification"/></th>
                        <th><s:label name="requestGroup.requestState"/></th>
                        <th><s:label name="biobank.name"/></th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:if test="${empty project.requestGroups}">
                    <tr><td colspan="6"><f:message key="empty"/></td></tr>
                    </c:if>

                    <c:forEach items="${project.requestGroups}" var="requestGroup">
                        <tr>
                            <td><c:out value="${requestGroup.id}"/></td>
                            <td><c:out value="${requestGroup.created}"/></td>
                            <td><c:out value="${requestGroup.lastModification}"/></td>
                            <td><f:message key="RequestState.${requestGroup.requestState}"/></td>
                            <td><c:out value="${requestGroup.biobank.name}"/></td>

                        </tr>

                    </c:forEach>
                    </tbody>
                </table>

            </c:forEach>
        </s:form>

    </s:layout-component>
</s:layout-render>