<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>
<s:useActionBean var="biobankSamplesBean" beanclass="cz.bbmri.action.biobank.BiobankSamplesActionBean"/>
<s:useActionBean var="biobankPatientsBean" beanclass="cz.bbmri.action.biobank.BiobankPatientsActionBean"/>
<s:useActionBean var="biobankRequestsBean" beanclass="cz.bbmri.action.biobank.BiobankSampleRequestsActionBean"/>
<s:useActionBean var="infrastructureBean" beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean"/>
<s:useActionBean var="biobankAdministratorsBean" beanclass="cz.bbmri.action.biobank.BiobankAdministratorsActionBean"/>

<s:layout-definition>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="biobankBean" event="detail">
    <li <c:if test="${ternarymenu == 'detail'}"> class="active" </c:if> >
        <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="detail">
            <s:param name="biobankId" value="${actionBean.biobankId}"/>
            <f:message key="cz.bbmri.action.biobank.BiobankActionBean.basicData"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="biobankAdministratorsBean" event="administratorsResolution">
    <li <c:if test="${ternarymenu == 'administrators'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.biobank.BiobankAdministratorsActionBean" event="administratorsResolution">
            <s:param name="biobankId" value="${actionBean.biobankId}"/>
            <f:message key="cz.bbmri.action.biobank.BiobankActionBean.administrators"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="biobankSamplesBean" event="display">
    <li <c:if test="${ternarymenu == 'samples'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.biobank.BiobankSamplesActionBean" event="display">
            <s:param name="biobankId" value="${actionBean.biobankId}"/>
            <f:message key="cz.bbmri.action.biobank.BiobankActionBean.samples"/></s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="biobankPatientsBean" event="display">
    <li <c:if test="${ternarymenu == 'patients'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.biobank.BiobankPatientsActionBean" event="display">
            <s:param name="biobankId" value="${actionBean.biobankId}"/>
            <f:message key="cz.bbmri.action.biobank.BiobankActionBean.patients"/></s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="biobankRequestsBean" event="display">
    <li <c:if test="${ternarymenu == 'sampleRequests'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.biobank.BiobankSampleRequestsActionBean" event="display">
            <s:param name="biobankId" value="${actionBean.biobankId}"/>
            <f:message key="cz.bbmri.action.biobank.BiobankActionBean.sampleRequests"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

    <li <c:if test="${ternarymenu == 'infrastructure'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean" event="all">
            <s:param name="biobankId" value="${actionBean.biobankId}"/>
            <f:message key="cz.bbmri.action.infrastructure.InfrastructureActionBean.occupancy"/>
        </s:link>
    </li>

<%-- -------------------------------------------------------------------- --%>

</s:layout-definition>