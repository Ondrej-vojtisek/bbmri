<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="patientBean" beanclass="cz.bbmri.action.patient.PatientActionBean"/>
<s:useActionBean var="patientModuleBean" beanclass="cz.bbmri.action.patient.PatientModuleActionBean"/>

<s:layout-definition>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="patientBean" event="detail">
    <li <c:if test="${ternarymenu == 'detail'}"> class="active" </c:if> >
        <s:link beanclass="cz.bbmri.action.patient.PatientActionBean" event="detail">
            <s:param name="biobankId" value="${actionBean.biobankId}"/>
            <s:param name="patientId" value="${patientBean.patientId}"/>
            <f:message key="cz.bbmri.action.patient.PatientActionBean.record"/>
        </s:link></li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="patientModuleBean" event="modulests">
    <li <c:if test="${ternarymenu == 'modulests'}"> class="active" </c:if> >
        <s:link beanclass="cz.bbmri.action.patient.PatientModuleActionBean" event="modulests">
            <s:param name="biobankId" value="${actionBean.biobankId}"/>
            <s:param name="patientId" value="${patientModuleBean.patientId}"/>
            <f:message key="cz.bbmri.action.patient.PatientModuleActionBean.modulests"/>
        </s:link></li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="patientModuleBean" event="modulelts">
    <li <c:if test="${ternarymenu == 'modulelts'}"> class="active" </c:if> >
        <s:link beanclass="cz.bbmri.action.patient.PatientModuleActionBean" event="modulelts">
            <s:param name="biobankId" value="${actionBean.biobankId}"/>
            <s:param name="patientId" value="${patientModuleBean.patientId}"/>
            <f:message key="cz.bbmri.action.patient.PatientModuleActionBean.modulelts"/>
        </s:link></li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

</s:layout-definition>

