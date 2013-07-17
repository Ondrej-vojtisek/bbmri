<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="sample.request" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleQuestionActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}"
                 primarymenu="biobank"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.SampleQuestionActionBean">
            <fieldset>
                <legend>Create sample request</legend>
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
                <f:message key="select_biobank"/>
                <s:select name="biobank.id">
                    <s:option value=""><f:message key="select_one"/></s:option>
                    <s:options-collection collection="${ab.biobanks}" label="name" value="id"/>
                </s:select>
                <p>Samples in database are characterized by these attributes: tissue type, TNM, pTNM, grading and diagnosis. It is recommended to specify requested samples with this attributes.</p>
                <p><s:label for="z5" name="sampleQuestion.specification"/></p>
                <s:textarea id="z5" name="sampleQuestion.specification"></s:textarea>

                <s:submit name="createSampleQuestion"><f:message key="request_sample"/></s:submit>
            </fieldset>

        </s:form>
    </s:layout-component>
</s:layout-render>