<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.patient.PatientActionBean.detail" var="title"/>
<s:useActionBean var="patientBean" beanclass="cz.bbmri.action.patient.PatientActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="patient"
                 ternarymenu="modulelts">

    <s:layout-component name="body">

        <div class="form-actions">
            <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="patients"
                    class="btn btn-primary btnMargin">
                <s:param name="biobankId" value="${patientBean.biobankId}"/>
                <f:message key="cz.bbmri.action.patient.PatientActionBean.backToBiobank"/>
            </s:link>

            <s:link beanclass="cz.bbmri.action.sample.CreateSampleActionBean" event="initial"
                    class="btn btn-primary">
                <s:param name="biobankId" value="${patientBean.biobankId}"/>
                <s:param name="moduleId" value="${patientBean.moduleLtsId}"/>
                <f:message key="cz.bbmri.action.sample.CreateSampleActionBean.addSample"/>
            </s:link>
        </div>

        <s:layout-render name="/webpages/component/samples.jsp" samples="${patientBean.samplesLTS}"/>

    </s:layout-component>
</s:layout-render>