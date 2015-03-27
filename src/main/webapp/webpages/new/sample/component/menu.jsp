<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="sampleBean" beanclass="cz.bbmri.action.sample.SampleActionBean"/>
<s:useActionBean var="sampleProjectsBean" beanclass="cz.bbmri.action.sample.SampleProjectsActionBean"/>
<s:useActionBean var="sampleReservationsBean" beanclass="cz.bbmri.action.sample.SampleReservationsActionBean"/>
<s:useActionBean var="samplePositionsBean" beanclass="cz.bbmri.action.sample.SamplePositionsActionBean"/>

<s:layout-definition>

    <ul class="nav nav-tabs">

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="sampleBean" event="detail">
            <li <c:if test="${active == 'detail'}"> class="active" </c:if> >
                <s:link beanclass="cz.bbmri.action.sample.SampleActionBean" event="detail">
                    <s:param name="sampleId" value="${actionBean.sampleId}"/>
                    <f:message key="detail"/>
                </s:link></li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="sampleProjectsBean" event="projects">
            <li <c:if test="${active == 'projects'}"> class="active" </c:if> >
                <s:link beanclass="cz.bbmri.action.sample.SampleProjectsActionBean" event="projects">
                    <s:param name="sampleId" value="${actionBean.sampleId}"/>
                    <f:message key="cz.bbmri.entities.Project.projects"/>
                </s:link></li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="sampleReservationsBean" event="reservations">
            <li <c:if test="${active == 'reservations'}"> class="active" </c:if> >
                <s:link beanclass="cz.bbmri.action.sample.SampleReservationsActionBean" event="reservations">
                    <s:param name="sampleId" value="${actionBean.sampleId}"/>
                    <f:message key="cz.bbmri.entities.SampleReservation.sampleReservations"/>
                </s:link></li>
        </security:allowed>

            <%-- -------------------------------------------------------------------- --%>

            <%--<security:allowed bean="samplePositionsBean" event="positions">--%>
            <%--<li <c:if test="${active == 'positions'}"> class="active" </c:if> >--%>
            <%--<s:link beanclass="cz.bbmri.action.sample.SamplePositionsActionBean" event="positions">--%>
            <%--<s:param name="sampleId" value="${actionBean.sampleId}"/>--%>
            <%--<f:message key="cz.bbmri.entitynfrastructure.Position.positions"/>--%>
            <%--</s:link></li>--%>
            <%--</security:allowed>--%>

            <%-- -------------------------------------------------------------------- --%>

    </ul>

</s:layout-definition>