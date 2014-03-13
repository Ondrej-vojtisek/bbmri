<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.allBiobanks" var="title"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.infrastructure.BoxActionBean" class="form-horizontal">

             <div class="control-group">
                 <s:label for="cz.bbmri.entities.infrastructure.Box.capacity" class="control-label"/>
                 <div class="controls">
                     <s:text name="rackBox.capacity"/>
                 </div>
             </div>

            <div class="control-group">
                 <s:label for="cz.bbmri.entities.infrastructure.Box.name" class="control-label"/>
                 <div class="controls">
                     <s:text name="rackBox.name"/>
                 </div>
            </div>

             <div class="control-group">
                 <s:label for="cz.bbmri.entities.infrastructure.Box.tempMin" class="control-label"/>
                 <div class="controls">
                     <s:text name="rackBox.tempMin"/>
                 </div>
             </div>

             <div class="control-group">
                 <s:label for="cz.bbmri.entities.infrastructure.Box.tempMax" class="control-label"/>
                 <div class="controls">
                     <s:text name="rackBox.tempMax"/>
                 </div>
             </div>

             <s:hidden name="rackId"/>

            <div class="form-actions">
                <s:submit name="createRackBox" class="btn btn-primary"/>
            </div>

        </s:form>

    </s:layout-component>
</s:layout-render>