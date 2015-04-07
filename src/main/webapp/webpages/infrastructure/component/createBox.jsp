<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.infrastructure.Container.name" class="control-label"/>
        <div class="controls">
            <stripes:text name="${boxType}.name"/>
        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.infrastructure.Container.capacity" class="control-label"/>
        <div class="controls">
            <stripes:text name="${boxType}.capacity"/>
        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.infrastructure.Container.tempMin" class="control-label"/>
        <div class="controls">
            <stripes:text name="${boxType}.tempMin"/>
        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.infrastructure.Container.tempMax" class="control-label"/>
        <div class="controls">
            <stripes:text name="${boxType}.tempMax"/>
        </div>
    </div>

</stripes:layout-definition>