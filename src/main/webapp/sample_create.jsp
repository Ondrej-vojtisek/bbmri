<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="samples.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Sample.SampleActionBean"/>
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
        <li><s:link href="/biobank_all.jsp"><f:message key="biobanks_all"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/biobank_create.jsp"><f:message key="biobank_create"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.biobank != null}">
            <li><s:link href="/sample_approve_request.jsp"><f:message key="approve_sample_request"/></s:link></li>
            <li class="active"><s:link href="/sample_create.jsp"><f:message key="sample_create"/></s:link></li>
            <li><s:link href="/sample_amortize.jsp"><f:message key="sample.amortize"/></s:link></li>
            <li><s:link href="/requestGroup_all.jsp"><f:message key="sample.requests"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.Sample.SampleActionBean">
            <fieldset>
                <legend><f:message key="add_sample_to_system"/></legend>
                <%@include file="/form/createSampleForm.jsp" %>
                <s:submit name="create"><f:message key="samples.add"/></s:submit>
            </fieldset>
            <fieldset>
                <legend>You can add here random samples for testing purposes</legend>
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