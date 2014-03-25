<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <s:layout-component name="body">


        <fieldset>
            <legend><f:message key="cz.bbmri.entities.infrastructure.Container.containers"/></legend>
            <div class="form-actions">
                <s:link beanclass="cz.bbmri.action.infrastructure.ContainerActionBean"
                        event="createContainerResolution"
                        class="btn btn-primary btnMargin">
                    <s:param name="biobankId" value="${actionBean.biobankId}"/>
                    <f:message key="cz.bbmri.action.infrastructure.InfrastructureActionBean.createContainer"/>
                </s:link>
            </div>

            <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                             pagination="${actionBean.pagination}"
                             componentManager="${actionBean.componentManager}"
                             targetBean="cz.bbmri.action.infrastructure.ContainerActionBean"
                             eventName="detail"
                             paramName="containerId"/>
        </fieldset>

        <fieldset>
            <legend><f:message key="cz.bbmri.entities.infrastructure.Box.boxes"/></legend>
            <div class="form-actions">
                <s:link beanclass="cz.bbmri.action.infrastructure.BoxActionBean"
                        event="createStandaloneBoxResolution"
                        class="btn btn-primary btnMargin">
                    <s:param name="biobankId" value="${actionBean.biobankId}"/>
                    <f:message key="cz.bbmri.action.infrastructure.InfrastructureActionBean.createBox"/>
                </s:link>
            </div>
            <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                             pagination="${actionBean.boxesPagination}"
                             componentManager="${actionBean.boxComponentManager}"
                             targetBean="cz.bbmri.action.infrastructure.BoxActionBean"
                             eventName="detail"
                             paramName="boxId"/>

        </fieldset>
    </s:layout-component>
</s:layout-render>