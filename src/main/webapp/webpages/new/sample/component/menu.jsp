<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="sampleActionBean" beanclass="cz.bbmri.action.SampleActionBean"/>

<s:layout-definition>

    <ul class="nav nav-tabs">

            <%-- -------------------------------------------------------------------- --%>

        <security:allowed bean="sampleActionBean" event="detail">
            <li <c:if test="${active == 'detail'}"> class="active" </c:if> >
                <s:link beanclass="cz.bbmri.action.SampleActionBean" event="detail">
                    <s:param name="id" value="${actionBean.id}"/>
                    <f:message key="detail"/>
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