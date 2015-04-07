<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="patientActionBean" beanclass="cz.bbmri.action.PatientActionBean"/>
<%--<stripes:useActionBean var="patientModuleBean" beanclass="cz.bbmri.action.patient.PatientModuleActionBean"/>--%>

<%--Set authProjectId of AuthotizationActionBean to enable security tag--%>
<core:set target="${actionBean}" property="authBiobankId" value="${actionBean.patient.biobank.id}"/>

<stripes:layout-definition>
    <ul class="nav nav-tabs">
            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="patientActionBean" event="detail">
            <li <core:if test="${active == 'detail'}"> class="active" </core:if> >
                <stripes:link beanclass="cz.bbmri.action.PatientActionBean" event="detail">
                    <stripes:param name="id" value="${patientActionBean.id}"/>
                    <format:message key="cz.bbmri.entity.Patient.patient"/>
                </stripes:link></li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="patientActionBean" event="samples">
            <li <core:if test="${active == 'samples'}"> class="active" </core:if> >
                <stripes:link beanclass="cz.bbmri.action.PatientActionBean" event="samples">
                    <stripes:param name="id" value="${patientActionBean.id}"/>
                    <format:message key="cz.bbmri.entity.Sample.samples"/>
                </stripes:link></li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

    </ul>

</stripes:layout-definition>

