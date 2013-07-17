<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="sample.request" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleQuestionActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}"
                 primarymenu="project"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}"
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

                <table id="sortableTable">
                    <thead>
                    <tr>
                        <th><f:message key="id"/></th>
                        <th><f:message key="requestGroup.created"/></th>
                        <th><f:message key="requestGroup.lastModification"/></th>
                        <th><f:message key="requestGroup.requestState"/></th>
                        <th><f:message key="biobank.name"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${project.requestGroups}" var="requestGroup">
                        <tr>
                            <td><c:out value="${requestGroup.id}"/></td>
                            <td><c:out value="${requestGroup.created}"/></td>
                            <td><c:out value="${requestGroup.lastModification}"/></td>
                            <td><f:message key="RequestState.${requestGroup.requestState}"/></td>
                            <td><c:out value="${requestGroup.biobank.name}"/></td>
                            <td><s:link beanclass="bbmri.action.SampleQuestionActionBean" event="requestGroupDetail">
                                <s:param name="requestGroup.id" value="${requestGroup.id}"/>
                                <f:message key="detail"/></s:link>
                            </td>
                        </tr>

                    </c:forEach>
                    </tbody>
                </table>

            </c:forEach>
        </s:form>

    </s:layout-component>
</s:layout-render>