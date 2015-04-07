<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.infrastructure.MonitoringActionBean"
                class="form-horizontal">

            <stripes:hidden name="containerId"/>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.infrastructure.monitoring.Monitoring.name" class="control-label"/>
                <div class="controls">
                    <stripes:text name="monitoring.name"/>
                </div>
            </div>

            <div class="control-group">
            <stripes:select name="monitoring.measurementType" class="btnMargin">
                <stripes:options-enumeration enum="cz.bbmri.entity.enumeration.MeasurementType"/>
            </stripes:select>
            </div>

            <stripes:submit name="createMonitoring" class="btn btn-info"/>

        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>