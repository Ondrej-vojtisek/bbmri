<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="sample.all" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleRequest.SampleRequestActionBean"/>
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
        <li><s:link href="/biobank_all.jsp"><f:message key="all"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/biobank_create.jsp"><f:message key="biobank_create"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.biobank != null}">
            <li><s:link href="/sample_approve_request.jsp"><f:message key="approve_sample_request"/></s:link></li>
            <li><s:link href="/sample_create.jsp"><f:message key="sample_create"/></s:link></li>
            <li><s:link href="/sample_release.jsp"><f:message key="sample.release"/></s:link></li>
            <li class="active"><s:link href="/sample_all.jsp"><f:message key="sample.all"/></s:link></li>
            <li><s:link href="/sample_released.jsp"><f:message key="sample.released"/></s:link></li>

        </c:if>
    </s:layout-component>

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.ReleaseSampleActionBean">
            <fieldset>
                <table>
                    <tr>
                        <th><s:label name="sample.sampleID"/></th>
                        <th><s:label name="sample.numOfSamples"/></th>
                        <th><s:label name="sample.numOfAvailable"/></th>
                        <th><s:label name="sample.tissueType"/></th>
                        <th><s:label name="sample.TNM"/></th>
                        <th><s:label name="sample.pTNM"/></th>
                        <th><s:label name="sample.grading"/></th>
                        <th><s:label name="sample.diagnosis"/></th>
                    </tr>

                    <c:forEach items="${ab.allSamples}" var="sample">
                        <tr>

                            <td><c:out value="${sample.sampleID}"/></td>
                            <td><c:out value="${sample.numOfSamples}"/></td>
                            <td><c:out value="${sample.numOfAvailable}"/></td>
                            <td><c:out value="${sample.tissueType}"/></td>
                            <td><c:out value="${sample.TNM}"/></td>
                            <td><c:out value="${sample.pTNM}"/></td>
                            <td><c:out value="${sample.grading}"/></td>
                            <td><c:out value="${sample.diagnosis}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>