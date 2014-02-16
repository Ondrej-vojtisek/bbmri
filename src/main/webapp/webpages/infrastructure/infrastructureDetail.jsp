<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.allBiobanks" var="title"/>
<s:useActionBean var="infrastructureBean" beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <s:layout-component name="body">


        <fieldset>
            <legend><f:message key="cz.bbmri.entities.infrastructure.Container.containers"/></legend>
            <div class="form-actions">
                <s:link beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean"
                        event="createContainerResolution"
                        class="btn btn-primary btnMargin">
                    <s:param name="biobankId" value="${infrastructureBean.biobankId}"/>
                    <f:message key="cz.bbmri.action.infrastructure.InfrastructureActionBean.createContainer"/>
                </s:link>
            </div>

            <s:layout-render name="/webpages/infrastructure/component/containers.jsp"/>
        </fieldset>

        <fieldset>
            <legend><f:message key="cz.bbmri.entities.infrastructure.Box.boxes"/></legend>
            <div class="form-actions">
                <s:link beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean"
                        event="createStandaloneBoxResolution"
                        class="btn btn-primary btnMargin">
                    <s:param name="biobankId" value="${infrastructureBean.biobankId}"/>
                    <f:message key="cz.bbmri.action.infrastructure.InfrastructureActionBean.createBox"/>
                </s:link>
            </div>
            <s:layout-render name="/webpages/infrastructure/component/boxes.jsp"
                             boxes="${infrastructureBean.standaloneBoxes}"/>

        </fieldset>
    </s:layout-component>
</s:layout-render>