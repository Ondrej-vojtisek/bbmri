<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="sample.withdraw" var="title"/>
<s:useActionBean var="ab" beanclass="cz.bbmri.action.sample.SampleActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 secondarymenu="sample_withdraw">

    <s:layout-component name="body">
        <s:form beanclass="cz.bbmri.action.sample.SampleActionBean">

            <fieldset>
                <legend><f:message key="selection_criteria"/></legend>
                <%@include file="/form/createSampleForm.jsp" %>
                <s:submit name="find"><f:message key="find"/></s:submit>
            </fieldset>
            <fieldset>
                <legend><f:message key="withdraw_samples_of_biobank"/></legend>
                <table cellspacing="0" class="tablesorter narrow">
                    <thead>
                    <tr>
                        <th><s:label name="sample.numOfSamples"/></th>
                        <th><s:label name="sample.numOfAvailable"/></th>
                        <th><s:label name="sample.grading"/></th>
                        <th><s:label name="sample.diagnosis"/></th>
                        <th><s:label name="sample.tissueType"/></th>
                        <th class="noSort"><s:label name="select"/></th>
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
                <s:submit name="requestSelected"><f:message key="proceed_with_selected"/></s:submit>
            </fieldset>


        </s:form>
    </s:layout-component>


</s:layout-render>