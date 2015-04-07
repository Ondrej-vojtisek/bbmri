<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="samples">

    <stripes:layout-component name="body">

        <stripes:layout-render name="/webpages/component/detail/patient/ribbon.jsp" record="${actionBean.patient}"/>

        <stripes:layout-render name="/webpages/component/detail/sample/ribbon.jsp" record="${actionBean.sample}"/>

        <stripes:form beanclass="cz.bbmri.action.sample.CreateSampleActionBean" class="form-horizontal">

            <core:if test="${actionBean.isTissue}">
                <stripes:layout-render name="/webpages/component/detail/tissue/ribbon.jsp"
                                 record="${actionBean.tissue}"/>
            </core:if>

            <core:if test="${actionBean.isGenome}">
                <stripes:layout-render name="/webpages/component/detail/genome/ribbon.jsp"
                                 record="${actionBean.genome}"/>
            </core:if>

            <core:if test="${actionBean.isDiagnosisMaterial}">
                <stripes:layout-render name="/webpages/component/detail/diagnosisMaterial/ribbon.jsp"
                                 record="${actionBean.diagnosisMaterial}"/>
            </core:if>

            <core:if test="${actionBean.isSerum}">
                <stripes:layout-render name="/webpages/component/detail/serum/ribbon.jsp"
                                 record="${actionBean.serum}"/>
            </core:if>

            <div class="form-actions">
                <stripes:submit name="confirmStep4" class="btn btn-primary btnMargin"/>
                <stripes:submit name="backFromStep4" class="btn btn-inverse"/>
            </div>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>