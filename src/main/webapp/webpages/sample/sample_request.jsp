<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="sample.request" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleQuestionActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.SampleQuestionActionBean">

            <fieldset>
                <legend><f:message key="sample_request_create"/></legend>
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
                <s:label name="select_biobank"/>
                <s:select name="biobank.id">
                    <s:option value=""><f:message key="select_one"/></s:option>
                    <s:options-collection collection="${ab.biobanks}" label="name" value="id"/>
                </s:select>
                <p><s:label name="samples_classification_description"/></p>
                <p><s:label for="z5" name="sampleQuestion.specification"/></p>
                <s:textarea id="z5" name="sampleQuestion.specification"/>

                <s:submit name="createSampleQuestion"><f:message key="request_sample"/></s:submit>
            </fieldset>

        </s:form>
    </s:layout-component>
</s:layout-render>