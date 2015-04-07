<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="patients">

    <stripes:layout-component name="body">

        <div class="form-actions">
            <stripes:link beanclass="cz.bbmri.action.patient.CreatePatientActionBean" event="initial"
                    class="btn btn-primary btnMargin">
                <stripes:param name="biobankId" value="${actionBean.biobankId}"/>
                <format:message key="cz.bbmri.action.patient.CreatePatientActionBean.addPatient"/>
            </stripes:link>

            <stripes:link beanclass="cz.bbmri.action.patient.FindPatientActionBean" event="findResolution"
                    class="btn btn-primary">
                <stripes:param name="biobankId" value="${actionBean.biobankId}"/>
                <format:message key="cz.bbmri.action.patient.FindPatientActionBean.findPatient"/>
            </stripes:link>
        </div>

        <stripes:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                         pagination="${actionBean.pagination}"
                         componentManager="${actionBean.componentManager}"
                         targetBean="cz.bbmri.action.patient.PatientActionBean"
                         eventName="detail"
                         paramName="patientId"/>

    </stripes:layout-component>
</stripes:layout-render>
