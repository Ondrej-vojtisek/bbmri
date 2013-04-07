<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="sample_question_detail" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleQuestionActionBean"/>

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
        </s:form>
        <s:link beanclass="bbmri.action.SampleQuestionActionBean" event="remove">
            <s:param name="sampleQuestion.id" value="${sampleQuestion.id}"/>
                   <f:message key="resolved"/>
        </s:link>

        <s:link beanclass="bbmri.action.SampleQuestionActionBean" event="back">
            <f:message key="back"/>
        </s:link>

    </s:layout-component>
</s:layout-render>