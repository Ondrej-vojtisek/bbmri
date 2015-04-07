<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <stripes:layout-component name="body">

        <stripes:layout-render name="/webpages/component/detail/container/ribbon.jsp"
                         record="${actionBean.container}"/>

        <%--TODO--%>
        <%--<div class="form-actions">--%>
            <%--<stripes:link beanclass="cz.bbmri.action.infrastructure.MonitoringActionBean"--%>
                    <%--event="createMonitoringResolution"--%>
                    <%--class="btn btn-primary btnMargin">--%>
                <%--<stripes:param name="containerId" value="${actionBean.containerId}"/>--%>
                <%--<format:message key="cz.bbmri.action.infrastructure.MonitoringActionBean.createMonitoring"/>--%>
            <%--</stripes:link>--%>

            <%--<stripes:link beanclass="cz.bbmri.action.infrastructure.MonitoringActionBean"--%>
                    <%--event="monitoring"--%>
                    <%--class="btn btn-info btnMargin">--%>
                <%--<stripes:param name="containerId" value="${actionBean.containerId}"/>--%>
                <%--<format:message key="cz.bbmri.action.infrastructure.MonitoringActionBean.viewMonitoring"/>--%>
            <%--</stripes:link>--%>
        <%--</div>--%>

        <fieldset>

            <legend><format:message key="cz.bbmri.entities.infrastructure.Rack.racks"/></legend>

            <div class="form-actions">
                <stripes:link beanclass="cz.bbmri.action.infrastructure.RackActionBean"
                        event="createRackResolution"
                        class="btn btn-primary btnMargin">
                    <stripes:param name="containerId" value="${actionBean.containerId}"/>
                    <format:message key="cz.bbmri.action.infrastructure.InfrastructureActionBean.createRack"/>
                </stripes:link>
            </div>

            <stripes:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                             pagination="${actionBean.pagination}"
                             componentManager="${actionBean.componentManager}"
                             targetBean="cz.bbmri.action.infrastructure.RackActionBean"
                             eventName="detail"
                             paramName="rackId"/>

        </fieldset>
    </stripes:layout-component>
</stripes:layout-render>