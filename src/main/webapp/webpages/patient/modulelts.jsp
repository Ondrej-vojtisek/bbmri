<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="patient"
                 ternarymenu="modulelts">

    <s:layout-component name="body">

        <div class="form-actions">

            <s:link beanclass="cz.bbmri.action.sample.CreateSampleActionBean" event="initial"
                    class="btn btn-primary">
                <s:param name="biobankId" value="${actionBean.patient.biobank.id}"/>
                <s:param name="moduleId" value="${actionBean.patient.moduleLTS.id}"/>
                <f:message key="cz.bbmri.action.sample.CreateSampleActionBean.addSample"/>
            </s:link>
        </div>

        <%--TODO--%>

        <%--<s:layout-render name="/webpages/component/samples.jsp" samples="${actionBean.patient.moduleLTS}"/>--%>

        <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                              pagination="${actionBean.pagination}"
                              componentManager="${actionBean.componentManager}"
                              targetBean="cz.bbmri.action.sample.SampleActionBean"
                              eventName="modulelts"
                              paramName="sampleId"/>


    </s:layout-component>
</s:layout-render>