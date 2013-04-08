<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="sample_question_detail" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleQuestionActionBean"/>

<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="primary_menu">
        <li><s:link href="/project_my_projects.jsp" ><f:message key="projects"/></s:link></li>
        <li class="active"><s:link href="/biobank_all.jsp" ><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp" ><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp" ><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
    </s:layout-component>

    <s:layout-component name="body">

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

            <s:text id="z1" readonly="true" name="sampleQuestion.project.name"/>

            <fieldset>
                <legend><f:message key="selection_criteria"/></legend>
                <p>Example: diagnosis "a%", tissueType "aa" -> diagnosis starts with 'a' AND tissueType = "aa"</p>
                <s:hidden name="sample.sampleID"/>
                <%@include file="/form/createSampleForm.jsp" %>
                <s:submit name="find"><f:message key="find"/></s:submit>
            </fieldset>

            <fieldset>
                <table id="sortableTable">
                    <thead>
                    <tr>
                        <th><s:label name="sample.TNM"/></th>
                        <th><s:label name="sample.pTNM"/></th>
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
                <s:submit name="requestSelected"><f:message key="proceed_selected"/></s:submit>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>