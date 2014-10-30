<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="patient"
                 ternarymenu="modulests">

    <s:layout-component name="body">

        <div class="form-actions">
            <%--TODO remove--%>
            <%--<s:link beanclass="cz.bbmri.action.biobank.BiobankPatientsActionBean" event="display"--%>
                    <%--class="btn btn-primary btnMargin">--%>
                <%--<s:param name="biobankId" value="${actionBean.patient.biobank.id}"/>--%>
                <%--<f:message key="cz.bbmri.action.patient.PatientActionBean.backToBiobank"/>--%>
            <%--</s:link>--%>

            <s:link beanclass="cz.bbmri.action.sample.CreateSampleActionBean" event="initial"
                    class="btn btn-primary">
                <s:param name="biobankId" value="${actionBean.patient.biobank.id}"/>
                <s:param name="moduleId" value="${actionBean.patient.moduleSTS.id}"/>
                <f:message key="cz.bbmri.action.sample.CreateSampleActionBean.addSample"/>
            </s:link>
        </div>

        <%--TODO--%>

        <%--<s:layout-render name="/webpages/component/samples.jsp" samples="${patientBean.samplesSTS}"/>--%>

        <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                         pagination="${actionBean.pagination}"
                         componentManager="${actionBean.componentManager}"
                         targetBean="cz.bbmri.action.sample.SampleActionBean"
                         eventName="modulests"
                         paramName="sampleId"/>

    </s:layout-component>
</s:layout-render>