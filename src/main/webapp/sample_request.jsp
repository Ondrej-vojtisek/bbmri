<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="sample.request" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleRequest.SampleRequestActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="primary_menu">
        <li><s:link href="/project_all.jsp"><f:message key="projects"/></s:link></li>
        <li><s:link href="/biobank_all.jsp"><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp"><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp"><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
    </s:layout-component>

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.SampleRequest.SampleRequestActionBean">
            <fieldset>
                <table>
                    <tr>
                        <th><s:label for="z1" name="project.name"/></th>
                        <td><s:text id="z1" readonly="true" name="project.name"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z2" name="project.fundingOrganization"/></th>
                        <td><s:text id="z2" readonly="true" name="project.fundingOrganization"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z3" name="project.projectState"/></th>
                        <td><s:text id="z3" readonly="true" name="project.projectState"/></td>
                    </tr>
                    <tr>
                        <th><label for="z10">Present samples in DB</label></th>
                        <td><s:text id="z10" name="count" readonly="true"/></td>
                    </tr>
                </table>
            </fieldset>

            <fieldset>
                <legend><f:message key="selection_criteria"/></legend>
                <p>Example: diagnosis "a%", tissueType "aa" -> diagnosis starts with 'a' AND tissueType = "aa"</p>
                <s:hidden name="sample.sampleID"/>
                <%@include file="/form/selectionCriteriaForm.jsp" %>
                <s:submit name="find"><f:message key="find"/></s:submit>
            </fieldset>

            <fieldset>
                <table border="1">
                    <tr>
                        <th><s:label name="sample.TNM"/></th>
                        <th><s:label name="sample.pTNM"/></th>
                        <th><s:label name="sample.grading"/></th>
                        <th><s:label name="sample.diagnosis"/></th>
                        <th><s:label name="sample.tissueType"/></th>
                        <th><s:label name="sample.numOfAvailable"/></th>
                        <th><s:label name="sample.biobank"/></th>
                        <th><s:label name="actions"/></th>
                    </tr>
                    <c:if test="${ab.resultCount == 0}">
                        <tr>
                            <td colspan="8"><s:label name="no_match_found"/></td>
                        </tr>
                    </c:if>

                    <c:forEach items="${ab.results}" var="sample">
                        <tr>
                            <td><c:out value="${sample.TNM}"/></td>
                            <td><c:out value="${sample.pTNM}"/></td>
                            <td><c:out value="${sample.grading}"/></td>
                            <td><c:out value="${sample.diagnosis}"/></td>
                            <td><c:out value="${sample.tissueType}"/></td>
                            <td><c:out value="${sample.numOfAvailable}"/></td>
                            <td><c:out value="${sample.biobank.name}"/></td>
                            <td><s:link beanclass="bbmri.action.SampleRequest.SampleRequestActionBean" event="request">
                                <s:param name="sample.id" value="${sample.id}"/><f:message
                                    key="sample.request"/></s:link>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>