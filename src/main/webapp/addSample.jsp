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
                 <%@include file="/form/createSampleForm.jsp" %>
                <s:submit name="create"><f:message key="samples.add"/></s:submit>
                 <th><label for="z9">Count</label></th>
                 <td><s:text id="z9" name="count"/></td>

                <s:submit name="generateRandomSample">Random</s:submit>
            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>