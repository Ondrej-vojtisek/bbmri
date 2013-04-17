<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="sample.withdraw" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Sample.SampleActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}">

    <s:layout-component name="primary_menu">


        <li><s:link href="/project_my_projects.jsp"><f:message key="projects"/></s:link></li>
        <li class="active"><s:link href="/biobank_all.jsp"><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp"><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp"><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
        <li><s:link href="/biobank_all.jsp"><f:message key="biobanks_all"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/biobank_create.jsp"><f:message key="biobank_create"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.biobank != null}">
            <li><s:link href="/sample_approve_request.jsp"><f:message key="approve_sample_request"/></s:link></li>
            <li><s:link href="/sample_create.jsp"><f:message key="sample_create"/></s:link></li>
            <li class="active"><s:link href="/sample_withdraw.jsp"><f:message key="sample.withdraw"/></s:link></li>
            <li><s:link href="/requestGroup_all.jsp"><f:message key="sample.requests"/></s:link></li>
        </c:if>
    </s:layout-component>


    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.Sample.SampleActionBean">

            <fieldset>
                <legend><f:message key="selection_criteria"/></legend>
                <p>Example: diagnosis "a%", tissueType "aa" -> diagnosis starts with 'a' AND tissueType = "aa"</p>
                <%@include file="/form/createSampleForm.jsp" %>
                <s:submit name="find"><f:message key="find"/></s:submit>
            </fieldset>
            <fieldset>
                <legend><f:message key="withdraw_samples_of_biobank"/></legend>
                <table id="sortableTable">
                    <thead>
                    <tr>
                        <th><s:label name="sample.numOfSamples"/></th>
                        <th><s:label name="sample.numOfAvailable"/></th>
                        <th><s:label name="sample.grading"/></th>
                        <th><s:label name="sample.diagnosis"/></th>
                        <th><s:label name="sample.tissueType"/></th>
                    </tr>
                    </thead>

                    <c:if test="${ab.resultCount == 0}">
                        <tr>
                            <td colspan="8"><s:label name="no_match_found"/></td>
                        </tr>
                    </c:if>
                    <tbody>
                    <c:forEach items="${ab.results}" var="sample">
                    <tr>
                        <td><c:out value="${sample.numOfSamples}"/></td>
                        <td><c:out value="${sample.numOfAvailable}"/></td>
                        <td><c:out value="${sample.grading}"/></td>
                        <td><c:out value="${sample.diagnosis}"/></td>
                        <td><c:out value="${sample.tissueType}"/></td>
                        <td>
                            <s:checkbox name="selectedSamples" value="${sample.id}"/>
                        </td>
                    </tr>
                    </c:forEach>
                    <tbody>
                </table>
                <s:submit name="withdrawSamples"><f:message key="request_selected"/></s:submit>
            </fieldset>


        </s:form>
    </s:layout-component>


</s:layout-render>