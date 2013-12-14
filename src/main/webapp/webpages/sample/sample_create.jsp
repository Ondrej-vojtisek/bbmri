<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="sample.import" var="title"/>
<s:useActionBean var="ab" beanclass="cz.bbmri.action.sample.CreateSampleActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 secondarymenu="sample_create">

    <s:layout-component name="body">
        <s:form beanclass="cz.bbmri.action.sample.CreateSampleActionBean">
            <fieldset>
                <legend><f:message key="add_sample_to_system"/></legend>
                <%@include file="/form/createSampleForm.jsp" %>
                <s:submit name="create"><f:message key="samples.add"/></s:submit>
            </fieldset>
            <fieldset>
                <legend><f:message key="add_random_samples"/></legend>
                <i><f:message key="for_testing_purpose"/></i>
                <table>
                    <tr>
                        <th><label for="z9"><f:message key="count"/></label></th>
                        <td><s:text id="z9" name="numOfRandom"/></td>
                    </tr>
                </table>
                <s:submit name="generateRandomSample"><f:message key="add_random_samples"/></s:submit>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>