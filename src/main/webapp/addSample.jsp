<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="samples.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.AddSampleActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">


        <s:form beanclass="bbmri.action.AddSampleActionBean">
            <fieldset>
                <legend><f:message key="add_sample_to_system"/></legend>
                <s:errors/>
                <table>
                    <tr>
                        <td><label for="z1"><f:message key="samples.id"/></label></td>
                        <td><s:text id="z1" name="sample.sampleID"/></td>

                    </tr>
                    <tr>
                        <td><label for="z2"><f:message key="samples.number_of_samples"/></label></td>
                        <td><s:text id="z2" name="sample.numOfSamples"/></td>
                    </tr>
                    <tr>
                        <td><label for="z3"><f:message key="samples.number_of_available_samples"/></label></td>
                        <td><s:text id="z3" name="sample.numOfAvailable"/></td>
                    </tr>
                    <tr>
                        <td><label for="z3"><f:message key="samples.tissue_type"/></label></td>
                        <td><s:text id="z3" name="sample.tissueType"/></td>
                        <td>string, length 2</td>
                    </tr>
                    <tr>
                        <td><label for="z3"><f:message key="samples.TNM"/></label></td>
                        <td><s:text id="z3" name="sample.TNM"/></td>
                        <td>max len 7</td>
                    </tr>
                    <tr>
                        <td><label for="z3"><f:message key="samples.pTNM"/></label></td>
                        <td><s:text id="z3" name="sample.pTNM"/></td>
                        <td>max len 7</td>
                    </tr>
                    <tr>
                        <td><label for="z3"><f:message key="samples.grading"/></label></td>
                        <td><s:text id="z3" name="sample.grading"/></td>
                        <td>minInclusive = "1" minExclusive = "9"</td>
                    </tr>
                    <tr>
                        <td><label for="z3"><f:message key="samples.diagnosis"/></label></td>
                        <td><s:text id="z3" name="sample.diagnosis"/></td>
                    </tr>
                </table>
                <s:submit name="create"><f:message key="samples.add"/></s:submit>
            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>