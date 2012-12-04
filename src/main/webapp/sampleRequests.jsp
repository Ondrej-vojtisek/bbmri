<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="sample.request" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleRequest.SampleRequestActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.SampleRequest.SampleRequestActionBean">
            <fieldset>
                <table>
                    <tr>
                        <th><s:label for="z1" name="project.name"/></th>
                        <td><s:text id="z1" name="project.name"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z2" name="project.fundingOrganization"/></th>
                        <td><s:text id="z2" name="project.fundingOrganization"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z3" name="project.projectState"/></th>
                        <td><s:text id="z3" name="project.projectState"/></td>
                    </tr>
                </table>
            </fieldset>

            <fieldset>
                 <legend><f:message key="selection_criteria"/></legend>
             <s:hidden name="sample.sampleID"/>
                <%@include file="/form/selectionCriteriaForm.jsp" %>
                <s:submit name="find"><f:message key="find"/></s:submit>
            </fieldset>

            <fieldset>

                <table border="1">
                    <tr>
                        <th><f:message key="sample.TNM"/></th>
                        <th><f:message key="sample.pTNM"/></th>
                        <th><f:message key="sample.grading"/></th>
                        <th><f:message key="sample.diagnosis"/></th>
                        <th><f:message key="sample.tissueType"/></th>
                        <th><f:message key="sample.numOfAvailable"/></th>
                        <th><f:message key="sample.biobank"/></th>
                    </tr>

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