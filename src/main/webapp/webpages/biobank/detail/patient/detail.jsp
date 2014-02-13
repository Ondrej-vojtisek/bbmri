<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.patient.PatientActionBean.detail" var="title"/>
<s:useActionBean var="patientBean" beanclass="cz.bbmri.action.patient.PatientActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="patient"
                 ternarymenu="detail">

    <s:layout-component name="body">

        <div class="form-actions">
            <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="patients"
                    class="btn btn-primary btnMargin">
                <s:param name="biobankId" value="${patientBean.biobankId}"/>
                <f:message key="cz.bbmri.action.patient.PatientActionBean.backToBiobank"/>
            </s:link>
        </div>

        <s:form beanclass="${patientBean.name}" class="form-horizontal">

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Patient.institutionId" class="control-label"/>
                <div class="controls">
                    <s:text name="patient.institutionId" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Patient.sex" class="control-label"/>
                <div class="controls">
                    <s:text name="patient.sex" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Patient.age" class="control-label"/>
                <div class="controls">
                    <s:text name="patient.age" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Patient.consent" class="control-label"/>
                <div class="controls">
                    <s:text name="patient.consent" readonly="true"/>
                </div>
            </div>

        </s:form>

    </s:layout-component>
</s:layout-render>