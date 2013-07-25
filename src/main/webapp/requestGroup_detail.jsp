<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="samples.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Sample.ApproveRequestActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.wholeName}"
                 primarymenu="biobank"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.Sample.ApproveRequestActionBean">
            <fieldset>
                <legend><f:message key="Request.group.detail"/></legend>
                <table>
                    <tr>
                        <th><s:label for="z1" name="project.name"/></th>
                        <td><s:text id="z1" readonly="true" name="requestGroup.project.name"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z2" name="requestState"/></th>
                        <td><f:message key="RequestState.${requestGroup.requestState}"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z3" name="project.owner"/></th>
                        <td><s:text id="z3" readonly="true" name="requestGroup.project.owner.wholeName"/></td>
                    </tr>
                </table>
            </fieldset>
            <fieldset>
                <table cellspacing="0" class="tablesorter">
                    <thead>
                    <tr>
                        <th><s:label name="request.numOfRequested"/></th>
                        <th><s:label name="sample.TNM"/></th>
                        <th><s:label name="sample.pTNM"/></th>
                        <th><s:label name="sample.grading"/></th>
                        <th><s:label name="sample.diagnosis"/></th>
                        <th><s:label name="sample.tissueType"/></th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:if test="${empty ab.requests}">
                                    <tr>
                                        <td colspan="6"><f:message key="empty"/></td>
                                    </tr>
                                </c:if>

                    <c:forEach items="${ab.requests}" var="request" varStatus="loop">
                    <tr>
                        <td><c:out value="${request.numOfRequested}"/></td>
                        <td><c:out value="${request.sample.TNM}"/></td>
                        <td><c:out value="${request.sample.pTNM}"/></td>
                        <td><c:out value="${request.sample.grading}"/></td>
                        <td><c:out value="${request.sample.diagnosis}"/></td>
                        <td><c:out value="${request.sample.tissueType}"/></td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>