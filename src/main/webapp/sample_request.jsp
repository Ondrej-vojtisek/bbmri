<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="sample.request" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleRequest.SampleRequestActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="primary_menu">
        <li><s:link href="/project_my_projects.jsp"><f:message key="projects"/></s:link></li>
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
                <legend></legend>
                <p>
                    <f:message key="project_approvment_doesnt_make_legal_claim"/>
                </p>
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
                        <th><s:label for="z3" name="project.judgedByUser"/></th>
                        <td><s:text id="z3" readonly="true" name="project.judgedByUser.wholeName"/></td>
                    </tr>
                </table>
            </fieldset>

            <fieldset>
                <legend><f:message key="selection_criteria"/></legend>
                <p>Example: diagnosis "a%", tissueType "aa" -> diagnosis starts with 'a' AND tissueType = "aa"</p>
                <%@include file="/form/selectionCriteriaForm.jsp" %>
                <s:submit name="createParametrizedRequest"><f:message key="request_sample"/></s:submit>
            </fieldset>

        </s:form>
    </s:layout-component>
</s:layout-render>