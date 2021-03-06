<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>
<s:useActionBean var="biobankSamplesBean" beanclass="cz.bbmri.action.biobank.BiobankSamplesActionBean"/>
<s:useActionBean var="biobankPatientsBean" beanclass="cz.bbmri.action.biobank.BiobankPatientsActionBean"/>
<s:useActionBean var="biobankRequestsBean" beanclass="cz.bbmri.action.biobank.BiobankSampleRequestsActionBean"/>
<s:useActionBean var="infrastructureBean" beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean"/>
<s:useActionBean var="biobankAdministratorsBean" beanclass="cz.bbmri.action.biobank.BiobankAdministratorsActionBean"/>
<s:useActionBean var="biobankAttachmentsBean" beanclass="cz.bbmri.action.biobank.BiobankAttachmentsActionBean"/>

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
            <s:link beanclass="cz.bbmri.action.biobank.BiobankAdministratorsActionBean"
                    event="administratorsResolution">
                <s:param name="biobankId" value="${actionBean.biobankId}"/>
                <f:message key="cz.bbmri.action.biobank.BiobankActionBean.administrators"/>
            </s:link>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="biobankAttachmentsBean" event="attachmentsResolution">
        <li <c:if test="${ternarymenu == 'attachments'}"> class="active" </c:if>>
            <s:link beanclass="cz.bbmri.action.biobank.BiobankAttachmentsActionBean"
                    event="administratorsResolution">
                <s:param name="biobankId" value="${actionBean.biobankId}"/>
                <f:message key="cz.bbmri.action.biobank.BiobankActionBean.attachments"/>
            </s:link>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>


    <security:allowed bean="biobankSamplesBean" event="display">
        <li <c:if test="${ternarymenu == 'samples'}"> class="active" </c:if>>
            <s:link beanclass="cz.bbmri.action.biobank.BiobankSamplesActionBean" event="display">
                <s:param name="biobankId" value="${actionBean.biobankId}"/>
                <f:message key="cz.bbmri.entities.sample.Sample.samples"/></s:link>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="biobankPatientsBean" event="display">
        <li <c:if test="${ternarymenu == 'patients'}"> class="active" </c:if>>
            <s:link beanclass="cz.bbmri.action.biobank.BiobankPatientsActionBean" event="display">
                <s:param name="biobankId" value="${actionBean.biobankId}"/>
                <f:message key="cz.bbmri.entities.Patient.patients"/></s:link>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="biobankRequestsBean" event="display">
        <li <c:if test="${ternarymenu == 'sampleRequests'}"> class="active" </c:if>>
            <s:link beanclass="cz.bbmri.action.biobank.BiobankSampleRequestsActionBean" event="display">
                <s:param name="biobankId" value="${actionBean.biobankId}"/>
                <f:message key="cz.bbmri.entities.SampleRequest.sampleRequests"/>
            </s:link>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>

    <%--<li <c:if test="${ternarymenu == 'infrastructure'}"> class="active" </c:if>>--%>
        <%--<s:link beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean" event="all">--%>
            <%--<s:param name="biobankId" value="${actionBean.biobankId}"/>--%>
            <%--<f:message key="cz.bbmri.entities.infrastructure.Infrastructure.infrastructure"/>--%>
        <%--</s:link>--%>
    <%--</li>--%>

    <%-- -------------------------------------------------------------------- --%>

    <%--TODO--%>
    <%--Temporary turned off --%>
    <%----%>
    <%--<li <c:if test="${ternarymenu == 'withdrawn'}"> class="active" </c:if>>--%>
        <%--<s:link beanclass="cz.bbmri.action.biobank.BiobankWithdrawSamplesActionBean" event="display">--%>
            <%--<s:param name="biobankId" value="${actionBean.biobankId}"/>--%>
            <%--<f:message key="cz.bbmri.action.biobank.BiobankWithdrawSamplesActionBean.selfRequisition"/>--%>
        <%--</s:link>--%>
    <%--</li>--%>

    <%-- -------------------------------------------------------------------- --%>

    <%--TODO--%>
    <%--<li <c:if test="${ternarymenu == 'monitoring'}"> class="active" </c:if>>--%>
    <%--<s:link beanclass="cz.bbmri.action.biobank.BiobankMonitoringActionBean" event="display">--%>
    <%--<s:param name="biobankId" value="${actionBean.biobankId}"/>--%>
    <%--<f:message key="cz.bbmri.entities.infrastructure.monitoring.Monitoring.monitoring"/>--%>
    <%--</s:link>--%>
    <%--</li>--%>

    <%-- -------------------------------------------------------------------- --%>

</s:layout-definition>