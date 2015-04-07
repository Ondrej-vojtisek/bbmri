<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="sample"
                 ternarymenu="detail">

    <stripes:layout-component name="body">

        <stripes:layout-render name="/webpages/component/detail/sample/ribbon.jsp"
                         record="${actionBean.sample}"/>

        <core:if test="${actionBean.isTissue}">
            <stripes:layout-render name="/webpages/component/detail/tissue/ribbon.jsp"
                             record="${actionBean.tissue}"/>
        </core:if>

        <core:if test="${actionBean.isSerum}">
            <stripes:layout-render name="/webpages/component/detail/serum/ribbon.jsp"
                             record="${actionBean.serum}"/>
        </core:if>

        <core:if test="${actionBean.isGenome}">
            <stripes:layout-render name="/webpages/component/detail/genome/ribbon.jsp"
                             record="${actionBean.genome}"/>
        </core:if>

        <core:if test="${actionBean.isDiagnosisMaterial}">
            <stripes:layout-render name="/webpages/component/detail/diagnosisMaterial/ribbon.jsp"
                             record="${actionBean.diagnosisMaterial}"/>
        </core:if>

        <div class="form-actions">
            <stripes:link beanclass="cz.bbmri.action.patient.PatientActionBean" event="detail"
                    class="btn btn-primary btnMargin">
                <stripes:param name="patientId" value="${actionBean.sample.module.patient.id}"/>
                <format:message key="cz.bbmri.action.sample.SampleActionBean.continueToPatient"/>
            </stripes:link>

            <%--TODO delete--%>
            <%--<stripes:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="detail"--%>
                    <%--class="btn btn-primary btnMargin">--%>
                <%--<stripes:param name="biobankId" value="${actionBean.sample.module.patient.biobank.id}"/>--%>
                <%--<format:message key="cz.bbmri.action.sample.SampleActionBean.continueToBiobank"/>--%>
            <%--</stripes:link>--%>
        </div>

    </stripes:layout-component>
</stripes:layout-render>