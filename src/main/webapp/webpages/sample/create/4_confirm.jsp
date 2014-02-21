<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="samples">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/component/detail/patient/ribbon.jsp" patient="${actionBean.patient}"/>

        <s:layout-render name="/webpages/component/detail/sample/ribbon.jsp" sample="${actionBean.sample}"/>

        <s:form beanclass="cz.bbmri.action.sample.CreateSampleActionBean" class="form-horizontal">

            <c:if test="${actionBean.isTissue}">
                <s:layout-render name="/webpages/component/detail/tissue/ribbon.jsp"
                                 tissue="${actionBean.tissue}"/>
            </c:if>

            <c:if test="${actionBean.isGenome}">
                <s:layout-render name="/webpages/component/detail/genome/ribbon.jsp"
                                 genome="${actionBean.genome}"/>
            </c:if>

            <c:if test="${actionBean.isDiagnosisMaterial}">
                <s:layout-render name="/webpages/component/detail/diagnosisMaterial/ribbon.jsp"
                                 diagnosisMaterial="${actionBean.diagnosisMaterial}"/>
            </c:if>

            <c:if test="${actionBean.isSerum}">
                <s:layout-render name="/webpages/component/detail/serum/ribbon.jsp"
                                 serum="${actionBean.serum}"/>
            </c:if>

            <div class="form-actions">
                <s:submit name="confirmStep4" class="btn btn-primary btnMargin"/>
                <s:submit name="backFromStep4" class="btn btn-inverse"/>
            </div>
        </s:form>
    </s:layout-component>
</s:layout-render>