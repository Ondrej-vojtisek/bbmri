<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.allBiobanks" var="title"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.infrastructure.RackActionBean" class="form-horizontal">

            <div class="control-group">
                <s:label for="cz.bbmri.entities.infrastructure.Rack.name" class="control-label"/>
                <div class="controls">
                    <s:text name="rack.name"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.infrastructure.Rack.capacity" class="control-label"/>
                <div class="controls">
                    <s:text name="rack.capacity"/>
                </div>
            </div>

            <s:hidden name="biobankId"/>
            <s:hidden name="containerId"/>

            <div class="form-actions">
                <s:submit name="createRack" class="btn btn-primary"/>
            </div>

        </s:form>

    </s:layout-component>
</s:layout-render>