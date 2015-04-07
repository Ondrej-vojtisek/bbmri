<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="sampleActionBean" beanclass="cz.bbmri.action.SampleActionBean"/>

<stripes:layout-definition>

    <ul class="nav nav-tabs">

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="sampleActionBean" event="detail">
            <li <core:if test="${active == 'detail'}"> class="active" </core:if> >
                <stripes:link beanclass="cz.bbmri.action.SampleActionBean" event="detail">
                    <stripes:param name="id" value="${actionBean.id}"/>
                    <format:message key="detail"/>
                </stripes:link></li>
        </security:allowed>


            <%-- -------------------------------------------------------------------- --%>

            <%--<security:allowed bean="samplePositionsBean" event="positions">--%>
            <%--<li <core:if test="${active == 'positions'}"> class="active" </core:if> >--%>
            <%--<stripes:link beanclass="cz.bbmri.action.sample.SamplePositionsActionBean" event="positions">--%>
            <%--<stripes:param name="sampleId" value="${actionBean.sampleId}"/>--%>
            <%--<format:message key="cz.bbmri.entitynfrastructure.Position.positions"/>--%>
            <%--</stripes:link></li>--%>
            <%--</security:allowed>--%>

            <%-- -------------------------------------------------------------------- --%>

    </ul>

</stripes:layout-definition>