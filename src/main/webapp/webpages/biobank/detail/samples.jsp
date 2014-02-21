<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.detail" var="title"/>
<s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="samples">

    <s:layout-component name="body">

        <div class="form-actions">
            <s:link beanclass="cz.bbmri.action.sample.CreateSampleActionBean" event="initial"
                    class="btn btn-primary">
                <s:param name="biobankId" value="${biobankBean.biobankId}"/>
                <f:message key="cz.bbmri.action.sample.CreateSampleActionBean.addSample"/>
            </s:link>
        </div>

        <s:layout-render name="/webpages/component/samples.jsp" samples="${biobankBean.samples}"/>

    </s:layout-component>
</s:layout-render>
