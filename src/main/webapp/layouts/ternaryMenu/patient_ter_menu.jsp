<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="patientBean" beanclass="cz.bbmri.action.patient.PatientActionBean"/>

<s:layout-definition>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="patientBean" event="detail">
    <li <c:if test="${ternarymenu == 'detail'}"> class="active" </c:if> >
        <s:link beanclass="cz.bbmri.action.patient.PatientActionBean" event="detail">
            <s:param name="patientId" value="${patientBean.patientId}"/>
            <f:message key="cz.bbmri.action.patient.PatientActionBean.record"/>
        </s:link></li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="patientBean" event="modulests">
    <li <c:if test="${ternarymenu == 'modulests'}"> class="active" </c:if> >
        <s:link beanclass="cz.bbmri.action.patient.PatientActionBean" event="modulests">
            <s:param name="patientId" value="${patientBean.patientId}"/>
            <f:message key="cz.bbmri.action.patient.PatientActionBean.modulests"/>
        </s:link></li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="patientBean" event="modulelts">
    <li <c:if test="${ternarymenu == 'modulelts'}"> class="active" </c:if> >
        <s:link beanclass="cz.bbmri.action.patient.PatientActionBean" event="modulelts">
            <s:param name="patientId" value="${patientBean.patientId}"/>
            <f:message key="cz.bbmri.action.patient.PatientActionBean.modulelts"/>
        </s:link></li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

</s:layout-definition>

