<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="samples.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.RequestGroupDetailActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.wholeName}"
                 primarymenu="biobank"
                 biobank="${null}"
                 administrator="${ab.loggedUser.administrator}">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.RequestGroupDetailActionBean">

            <fieldset>
                <table>
                    <tr>
                        <th><s:label for="z1" name="request.numOfRequested"/></th>
                        <td><s:text id="z1" name="request.numOfRequested"/></td>
                    </tr>
                </table>
            </fieldset>

            <fieldset>
                <legend><f:message key="Request.group.detail"/></legend>
                <table>
                    <tr>
                        <th><s:label for="z1" name="project.name"/></th>
                        <td><s:text id="z1" readonly="true" name="requestGroup.project.name"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z2" name="requestState"/></th>
                        <td><s:text id="z2" readonly="true" name="requestGroup.requestState"/></td>
                    </tr>

                    <tr>
                        <th><s:label for="z3" name="project.owner"/></th>
                        <td><s:text id="z3" readonly="true" name="requestGroup.project.owner.wholeName"/></td>
                    </tr>
                </table>
            </fieldset>

            <fieldset>
                <legend><f:message key="request.sample"/></legend>
                <table>
                    <tr>
                        <th><label for="z1"><s:label name="sample.sampleID"/></label></th>
                        <td><s:text id="z1" name="sample.sampleID" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><label for="z2"><s:label name="sample.numOfSamples"/></label></th>
                        <td><s:text id="z2" name="sample.numOfSamples" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><label for="z3"><s:label name="sample.numOfAvailable"/></label></th>
                        <td><s:text id="z3" name="sample.numOfAvailable" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><label for="z4"><s:label name="sample.tissueType"/></label></th>
                        <td><s:text id="z4" name="sample.tissueType" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><label for="z5"><s:label name="sample.TNM"/></label></th>
                        <td><s:text id="z5" name="sample.TNM" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><label for="z6"><s:label name="sample.pTNM"/></label></th>
                        <td><s:text id="z6" name="sample.pTNM" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><label for="z7"><s:label name="sample.grading"/></label></th>
                        <td><s:text id="z7" name="sample.grading" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><label for="z8"><s:label name="sample.diagnosis"/></label></th>
                        <td><s:text id="z8" name="sample.diagnosis" readonly="true"/></td>
                    </tr>
                </table>
            </fieldset>

            <s:submit name="update"><f:message key="save"/></s:submit>
            <s:submit name="cancel"><f:message key="cancel"/></s:submit>

        </s:form>
    </s:layout-component>
</s:layout-render>