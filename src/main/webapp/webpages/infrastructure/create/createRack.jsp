<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.infrastructure.RackActionBean" class="form-horizontal">

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.infrastructure.Rack.name" class="control-label"/>
                <div class="controls">
                    <stripes:text name="rack.name"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.infrastructure.Rack.capacity" class="control-label"/>
                <div class="controls">
                    <stripes:text name="rack.capacity"/>
                </div>
            </div>

            <stripes:hidden name="biobankId"/>
            <stripes:hidden name="containerId"/>

            <div class="form-actions">
                <stripes:submit name="createRack" class="btn btn-primary"/>
            </div>

        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>