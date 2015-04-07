<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.infrastructure.BoxActionBean" class="form-horizontal">

             <div class="control-group">
                 <stripes:label for="cz.bbmri.entities.infrastructure.Box.capacity" class="control-label"/>
                 <div class="controls">
                     <stripes:text name="rackBox.capacity"/>
                 </div>
             </div>

            <div class="control-group">
                 <stripes:label for="cz.bbmri.entities.infrastructure.Box.name" class="control-label"/>
                 <div class="controls">
                     <stripes:text name="rackBox.name"/>
                 </div>
            </div>

             <div class="control-group">
                 <stripes:label for="cz.bbmri.entities.infrastructure.Box.tempMin" class="control-label"/>
                 <div class="controls">
                     <stripes:text name="rackBox.tempMin"/>
                 </div>
             </div>

             <div class="control-group">
                 <stripes:label for="cz.bbmri.entities.infrastructure.Box.tempMax" class="control-label"/>
                 <div class="controls">
                     <stripes:text name="rackBox.tempMax"/>
                 </div>
             </div>

             <stripes:hidden name="rackId"/>

            <div class="form-actions">
                <stripes:submit name="createRackBox" class="btn btn-primary"/>
            </div>

        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>