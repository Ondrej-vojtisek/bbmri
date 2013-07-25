<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="sample_question_detail" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleQuestionActionBean"/>

<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.wholeName}"
                 primarymenu="biobank"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}">

    <s:layout-component name="body">

        <fieldset>
        <legend><f:message key="sample.question"/></legend>
        <s:form beanclass="bbmri.action.SampleQuestionActionBean">
            <table>
                <tr>
                    <th><s:label for="z1" name="project.name"/></th>
                    <td><s:text id="z1" readonly="true" name="sampleQuestion.project.name"/></td>
                </tr>
                <tr>
                    <th><s:label for="z2" name="project.owner"/></th>
                    <td><s:text id="z2" readonly="true" name="sampleQuestion.project.owner"/></td>
                </tr>
                <tr>
                    <th><s:label for="z3" name="sampleQuestion.specification"/></th>
                </tr>
            </table>
            <s:textarea readonly="true" id="z3" name="sampleQuestion.specification"></s:textarea>
            <fieldset>

            <fieldset>
                <legend><f:message key="selection_criteria"/></legend>
        <s:form beanclass="bbmri.action.SampleQuestionActionBean">
                <s:hidden name="sample.sampleID"/>
                <%@include file="/form/createSampleForm.jsp" %>
                <s:submit name="find"><f:message key="find"/></s:submit>
        </s:form>
            </fieldset>

            <fieldset>
                <table cellspacing="0" class="tablesorter narrow">
                    <thead>
                    <tr>
                        <th><s:label name="sample.numOfSamples"/></th>
                        <th><s:label name="sample.numOfAvailable"/></th>
                        <th><s:label name="sample.TNM"/></th>
                        <th><s:label name="sample.pTNM"/></th>
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
                        <td><c:out value="${sample.TNM}"/></td>
                        <td><c:out value="${sample.pTNM}"/></td>
                        <td><c:out value="${sample.grading}"/></td>
                        <td><c:out value="${sample.diagnosis}"/></td>
                        <td><c:out value="${sample.tissueType}"/></td>
                        <td><s:checkbox name="selected" value="${sample.id}"/></td>
                    </tr>
                    </c:forEach>
                    <tbody>
                </table>
                <s:submit name="requestSelected"><f:message key="proceed_with_selected"/></s:submit>
                <s:submit name="reject"><f:message key="reject"/></s:submit>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>