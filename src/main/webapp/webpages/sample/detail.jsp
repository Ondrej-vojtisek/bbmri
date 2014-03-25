<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="sample"
                 ternarymenu="detail">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/component/detail/sample/ribbon.jsp"
                         record="${actionBean.sample}"/>

        <c:if test="${actionBean.isTissue}">
            <s:layout-render name="/webpages/component/detail/tissue/ribbon.jsp"
                             record="${actionBean.tissue}"/>
        </c:if>

        <c:if test="${actionBean.isSerum}">
            <s:layout-render name="/webpages/component/detail/serum/ribbon.jsp"
                             record="${actionBean.serum}"/>
        </c:if>

        <c:if test="${actionBean.isGenome}">
            <s:layout-render name="/webpages/component/detail/genome/ribbon.jsp"
                             record="${actionBean.genome}"/>
        </c:if>

        <c:if test="${actionBean.isDiagnosisMaterial}">
            <s:layout-render name="/webpages/component/detail/diagnosisMaterial/ribbon.jsp"
                             record="${actionBean.diagnosisMaterial}"/>
        </c:if>

        <div class="form-actions">
            <s:link beanclass="cz.bbmri.action.patient.PatientActionBean" event="detail"
                    class="btn btn-primary btnMargin">
                <s:param name="patientId" value="${actionBean.sample.module.patient.id}"/>
                <f:message key="cz.bbmri.action.sample.SampleActionBean.continueToPatient"/>
            </s:link>

            <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="detail"
                    class="btn btn-primary btnMargin">
                <s:param name="biobankId" value="${actionBean.sample.module.patient.biobank.id}"/>
                <f:message key="cz.bbmri.action.sample.SampleActionBean.continueToBiobank"/>
            </s:link>
        </div>

    </s:layout-component>
</s:layout-render>