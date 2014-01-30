<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="sample.all" var="title"/>
<s:useActionBean var="ab" beanclass="cz.bbmri.action.sample.SampleActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 secondarymenu="sample_all">

    <s:layout-component name="body">
        <s:form beanclass="cz.bbmri.action.sample.SampleActionBean">
            <fieldset>
                <legend><f:message key="sample.all"/></legend>
            <table cellspacing="0" class="tablesorter narrow">
                    <thead>
                    <tr>
                        <th><s:label name="sample.numOfSamples"/></th>
                        <th><s:label name="sample.numOfAvailable"/></th>
                        <th><s:label name="sample.grading"/></th>
                        <th><s:label name="sample.diagnosis"/></th>
                        <th><s:label name="sample.tissueType"/></th>
                        <th class="noSort"><s:label name="actions"/></th>
                    </tr>
                    </thead>

                    <c:if test="${ab.samplesSize == 0}">
                        <tr>
                            <td colspan="8"><s:label name="no_match_found"/></td>
                        </tr>
                    </c:if>
                    <tbody>
                    <c:forEach items="${ab.samples}" var="sample">
                    <tr <c:if test="${not sample.filled}"> class="not_filled" </c:if>>
                        <td><c:out value="${sample.numOfSamples}"/></td>
                        <td><c:out value="${sample.numOfAvailable}"/></td>
                        <td><c:out value="${sample.grading}"/></td>
                        <td><c:out value="${sample.diagnosis}"/></td>
                        <td><c:out value="${sample.tissueType}"/></td>
                        <td>
                            <s:link beanclass="cz.bbmri.action.sample.SampleActionBean" event="edit">
                                <s:param name="sample.id" value="${sample.id}"/><f:message
                                key="edit"/></s:link>
                        </td>
                    </tr>
                    </c:forEach>
                    <tbody>
                </table>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>