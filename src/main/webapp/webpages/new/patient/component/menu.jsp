<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="patientActionBean" beanclass="cz.bbmri.action.PatientActionBean"/>
<%--<s:useActionBean var="patientModuleBean" beanclass="cz.bbmri.action.patient.PatientModuleActionBean"/>--%>

<s:layout-definition>
    <ul class="nav nav-tabs">
            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="patientActionBean" event="detail">
            <li <c:if test="${active == 'detail'}"> class="active" </c:if> >
                <s:link beanclass="cz.bbmri.action.PatientActionBean" event="detail">
                    <s:param name="id" value="${patientActionBean.id}"/>
                    <f:message key="cz.bbmri.entity.Patient.patient"/>
                </s:link></li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="patientActionBean" event="samples">
            <li <c:if test="${active == 'samples'}"> class="active" </c:if> >
                <s:link beanclass="cz.bbmri.action.PatientActionBean" event="samples">
                    <s:param name="id" value="${patientActionBean.id}"/>
                    <f:message key="cz.bbmri.entity.Sample.samples"/>
                </s:link></li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

    </ul>

</s:layout-definition>

