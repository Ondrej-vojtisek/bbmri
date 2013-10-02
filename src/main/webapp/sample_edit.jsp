<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="sample.edit" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.sample.EditSampleActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.wholeName}"
                 primarymenu="biobank"
                 biobank="${null}"
                 administrator="${ab.loggedUser.administrator}">

    <s:layout-component name="body">
        <fieldset>
            <legend><f:message key="add_sample_to_system"/></legend>
            <s:form beanclass="bbmri.action.sample.EditSampleActionBean">
                <%@include file="/form/createSampleForm.jsp" %>
                <s:submit name="save"><f:message key="save"/></s:submit>
                <s:submit name="cancel"><f:message key="cancel"/></s:submit>
            </s:form>
            </fieldset>
    </s:layout-component>
</s:layout-render>