<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="sampleBean" beanclass="cz.bbmri.action.sample.SampleActionBean"/>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="sampleBean" event="detail">
    <li <c:if test="${ternarymenu == 'detail'}"> class="active" </c:if> >
        <s:link beanclass="cz.bbmri.action.sample.SampleActionBean" event="detail">
            <s:param name="sampleId" value="${sampleBean.sampleId}"/>
            <s:param name="biobankId" value="${sampleBean.biobankId}"/>
            <f:message key="detail"/>
        </s:link></li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="sampleBean" event="projects">
    <li <c:if test="${ternarymenu == 'projects'}"> class="active" </c:if> >
        <s:link beanclass="cz.bbmri.action.sample.SampleActionBean" event="projects">
            <s:param name="sampleId" value="${sampleBean.sampleId}"/>
            <s:param name="biobankId" value="${sampleBean.biobankId}"/>
            <f:message key="projects"/>
        </s:link></li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="sampleBean" event="reservations">
    <li <c:if test="${ternarymenu == 'reservations'}"> class="active" </c:if> >
        <s:link beanclass="cz.bbmri.action.sample.SampleActionBean" event="reservations">
            <s:param name="sampleId" value="${sampleBean.sampleId}"/>
            <s:param name="biobankId" value="${sampleBean.biobankId}"/>
            <f:message key="reservations"/>
        </s:link></li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="sampleBean" event="positions">
    <li <c:if test="${ternarymenu == 'positions'}"> class="active" </c:if> >
        <s:link beanclass="cz.bbmri.action.sample.SampleActionBean" event="positions">
            <s:param name="sampleId" value="${sampleBean.sampleId}"/>
            <s:param name="biobankId" value="${sampleBean.biobankId}"/>
            <f:message key="positions"/>
        </s:link></li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>