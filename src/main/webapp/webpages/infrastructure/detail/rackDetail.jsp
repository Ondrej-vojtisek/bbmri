<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <stripes:layout-component name="body">

        <stripes:layout-render name="/webpages/component/detail/container/ribbon.jsp"
                         record="${actionBean.rack.container}"/>

        <stripes:layout-render name="/webpages/component/detail/rack/ribbon.jsp"
                         record="${actionBean.rack}"/>


        <fieldset>
            <legend><format:message key="cz.bbmri.entities.infrastructure.Box.boxes"/></legend>

            <div class="form-actions">
                <stripes:link beanclass="cz.bbmri.action.infrastructure.BoxActionBean"
                        event="createRackBoxResolution"
                        class="btn btn-primary btnMargin">
                    <stripes:param name="rackId" value="${actionBean.rackId}"/>
                    <format:message key="cz.bbmri.action.infrastructure.InfrastructureActionBean.createBox"/>
                </stripes:link>
            </div>

            <stripes:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                             pagination="${actionBean.pagination}"
                             componentManager="${actionBean.componentManager}"
                             targetBean="cz.bbmri.action.infrastructure.BoxActionBean"
                             eventName="detail"
                             paramName="boxId"/>
        </fieldset>

    </stripes:layout-component>
</stripes:layout-render>