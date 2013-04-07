<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<f:message key="samples.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Sample.ApproveRequestActionBean"/>
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
                <li class="active"><s:link href="/sample_approve_request.jsp"><f:message key="approve_sample_request"/></s:link></li>
                <li><s:link href="/sample_amortize.jsp"><f:message key="sample.amortize"/></s:link></li>
                <li><s:link href="/sample_create.jsp"><f:message key="sample_create"/></s:link></li>
            </c:if>
        </c:if>
    </s:layout-component>

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
                <table id="sortableTable">
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