<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/component/detail/container/ribbon.jsp"
                         record="${actionBean.container}"/>

        <div class="form-actions">
            <s:link beanclass="cz.bbmri.action.infrastructure.MonitoringActionBean"
                    event="createMonitoringResolution"
                    class="btn btn-primary btnMargin">
                <s:param name="containerId" value="${actionBean.containerId}"/>
                <f:message key="cz.bbmri.action.infrastructure.MonitoringActionBean.createMonitoring"/>
            </s:link>

            <s:link beanclass="cz.bbmri.action.infrastructure.MonitoringActionBean"
                    event="monitoring"
                    class="btn btn-info btnMargin">
                <s:param name="containerId" value="${actionBean.containerId}"/>
                <f:message key="cz.bbmri.action.infrastructure.MonitoringActionBean.viewMonitoring"/>
            </s:link>
        </div>

        <fieldset>

            <legend><f:message key="cz.bbmri.entities.infrastructure.Rack.racks"/></legend>

            <div class="form-actions">
                <s:link beanclass="cz.bbmri.action.infrastructure.RackActionBean"
                        event="createRackResolution"
                        class="btn btn-primary btnMargin">
                    <s:param name="containerId" value="${actionBean.containerId}"/>
                    <f:message key="cz.bbmri.action.infrastructure.InfrastructureActionBean.createRack"/>
                </s:link>
            </div>

            <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                             pagination="${actionBean.pagination}"
                             componentManager="${actionBean.componentManager}"
                             targetBean="cz.bbmri.action.infrastructure.RackActionBean"
                             eventName="detail"
                             paramName="rackId"/>

        </fieldset>
    </s:layout-component>
</s:layout-render>