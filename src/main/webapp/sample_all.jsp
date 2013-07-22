<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="sample.all" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Sample.SampleActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}"
                 primarymenu="biobank"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}"
                 secondarymenu="sample_all">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.Sample.SampleActionBean">
                <legend><f:message key="sample.all"/></legend>
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
                            <s:link beanclass="bbmri.action.Sample.SampleActionBean" event="edit">
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