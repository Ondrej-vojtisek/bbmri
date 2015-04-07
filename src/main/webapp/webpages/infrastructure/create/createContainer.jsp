<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.infrastructure.ContainerActionBean" class="form-horizontal">
            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.infrastructure.Container.name" class="control-label"/>
                <div class="controls">
                    <stripes:text name="container.name"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.infrastructure.Container.location" class="control-label"/>
                <div class="controls">
                    <stripes:text name="container.location"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.infrastructure.Container.capacity" class="control-label"/>
                <div class="controls">
                    <stripes:text name="container.capacity"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.infrastructure.Container.tempMin" class="control-label"/>
                <div class="controls">
                    <stripes:text name="container.tempMin"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.infrastructure.Container.tempMax" class="control-label"/>
                <div class="controls">
                    <stripes:text name="container.tempMax"/>
                </div>
            </div>

            <stripes:hidden name="biobankId"/>

            <div class="form-actions">
            <stripes:submit name="createContainer" class="btn btn-primary"/>
            </div>

        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>