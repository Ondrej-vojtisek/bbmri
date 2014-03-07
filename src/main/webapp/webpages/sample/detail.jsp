<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.sample.SampleActionBean.detail" var="title"/>
<s:useActionBean var="sampleBean" beanclass="cz.bbmri.action.sample.SampleActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="sample"
                 ternarymenu="detail">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/component/detail/sample/ribbon.jsp"
                         record="${sampleBean.sample}"/>

        <c:if test="${sampleBean.isTissue}">
            <s:layout-render name="/webpages/component/detail/tissue/ribbon.jsp"
                             record="${sampleBean.tissue}"/>
        </c:if>

        <c:if test="${sampleBean.isSerum}">
            <s:layout-render name="/webpages/component/detail/serum/ribbon.jsp"
                             record="${sampleBean.serum}"/>
        </c:if>

        <c:if test="${sampleBean.isGenome}">
            <s:layout-render name="/webpages/component/detail/genome/ribbon.jsp"
                             record="${sampleBean.genome}"/>
        </c:if>

        <c:if test="${sampleBean.isDiagnosisMaterial}">
            <s:layout-render name="/webpages/component/detail/diagnosisMaterial/ribbon.jsp"
                             record="${sampleBean.diagnosisMaterial}"/>
        </c:if>

        <div class="form-actions">
            <s:link beanclass="cz.bbmri.action.patient.PatientActionBean" event="detail"
                    class="btn btn-primary btnMargin">
                <s:param name="patientId" value="${sampleBean.sample.module.patient.id}"/>
                <f:message key="cz.bbmri.action.sample.SampleActionBean.continueToPatient"/>
            </s:link>

            <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="detail"
                    class="btn btn-primary btnMargin">
                <s:param name="biobankId" value="${sampleBean.sample.module.patient.biobank.id}"/>
                <f:message key="cz.bbmri.action.sample.SampleActionBean.continueToBiobank"/>
            </s:link>
        </div>

    </s:layout-component>
</s:layout-render>