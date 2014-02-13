<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.patient.PatientActionBean.detail" var="title"/>
<s:useActionBean var="patientBean" beanclass="cz.bbmri.action.patient.PatientActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="patient"
                 ternarymenu="modulests">

    <s:layout-component name="body">

        <div class="form-actions">
            <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="patients"
                    class="btn btn-primary btnMargin">
                <s:param name="biobankId" value="${patientBean.biobankId}"/>
                <f:message key="cz.bbmri.action.patient.PatientActionBean.backToBiobank"/>
            </s:link>
        </div>

     MODULE STS


    </s:layout-component>
</s:layout-render>