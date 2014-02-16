<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.allBiobanks" var="title"/>
<s:useActionBean var="infrastructureBean" beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/component/detail/container/ribbon.jsp"
                         container="${infrastructureBean.container}"/>


        <fieldset>

            <legend><f:message key="cz.bbmri.entities.infrastructure.Rack.racks"/></legend>

            <div class="form-actions">
                <s:link beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean"
                        event="createRackResolution"
                        class="btn btn-primary btnMargin">
                    <s:param name="biobankId" value="${infrastructureBean.biobankId}"/>
                    <s:param name="containerId" value="${infrastructureBean.containerId}"/>
                    <f:message key="cz.bbmri.action.infrastructure.InfrastructureActionBean.createRack"/>
                </s:link>
            </div>

            <s:layout-render name="/webpages/infrastructure/component/racks.jsp"/>

        </fieldset>
    </s:layout-component>
</s:layout-render>