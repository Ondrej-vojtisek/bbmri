<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.infrastructure.MonitoringActionBean"
                class="form-horizontal">

            <s:hidden name="containerId"/>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.infrastructure.monitoring.Monitoring.name" class="control-label"/>
                <div class="controls">
                    <s:text name="monitoring.name"/>
                </div>
            </div>

            <div class="control-group">
            <s:select name="monitoring.measurementType" class="btnMargin">
                <s:options-enumeration enum="cz.bbmri.entity.enumeration.MeasurementType"/>
            </s:select>
            </div>

            <s:submit name="createMonitoring" class="btn btn-info"/>

        </s:form>

    </s:layout-component>
</s:layout-render>