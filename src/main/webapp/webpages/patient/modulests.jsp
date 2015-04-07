<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="patient"
                 ternarymenu="modulests">

    <stripes:layout-component name="body">

        <div class="form-actions">

            <stripes:link beanclass="cz.bbmri.action.sample.CreateSampleActionBean" event="initial"
                    class="btn btn-primary">
                <stripes:param name="biobankId" value="${actionBean.patient.biobank.id}"/>
                <stripes:param name="moduleId" value="${actionBean.patient.moduleSTS.id}"/>
                <format:message key="cz.bbmri.action.sample.CreateSampleActionBean.addSample"/>
            </stripes:link>
        </div>

        <%--TODO--%>

        <%--<stripes:layout-render name="/webpages/component/samples.jsp" samples="${patientBean.samplesSTS}"/>--%>

        <stripes:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                         pagination="${actionBean.pagination}"
                         componentManager="${actionBean.componentManager}"
                         targetBean="cz.bbmri.action.sample.SampleActionBean"
                         eventName="modulests"
                         paramName="sampleId"/>

    </stripes:layout-component>
</stripes:layout-render>