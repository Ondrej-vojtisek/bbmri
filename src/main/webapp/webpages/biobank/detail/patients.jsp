<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%--<f:message key="cz.bbmri.action.biobank.BiobankActionBean.detail" var="title"/>--%>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="patients">

    <s:layout-component name="body">

        <div class="form-actions">
            <s:link beanclass="cz.bbmri.action.patient.CreatePatientActionBean" event="initial"
                    class="btn btn-primary btnMargin">
                <s:param name="biobankId" value="${actionBean.biobankId}"/>
                <f:message key="cz.bbmri.action.patient.CreatePatientActionBean.addPatient"/>
            </s:link>

            <s:link beanclass="cz.bbmri.action.patient.FindPatientActionBean" event="findResolution"
                    class="btn btn-primary">
                <s:param name="biobankId" value="${actionBean.biobankId}"/>
                <f:message key="cz.bbmri.action.patient.FindPatientActionBean.findPatient"/>
            </s:link>
        </div>

        <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                         pagination="${actionBean.pagination}"
                         componentManager="${actionBean.componentManager}"
                         targetBean="cz.bbmri.action.patient.PatientActionBean"
                         eventName="detail"
                         paramName="patientId"/>

    </s:layout-component>
</s:layout-render>
