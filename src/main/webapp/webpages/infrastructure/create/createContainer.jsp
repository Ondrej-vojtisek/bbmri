<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.allBiobanks" var="title"/>
<s:useActionBean var="infrastructureBean" beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean" class="form-horizontal">
            <div class="control-group">
                <s:label for="cz.bbmri.entities.infrastructure.Container.name" class="control-label"/>
                <div class="controls">
                    <s:text name="container.name"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.infrastructure.Container.location" class="control-label"/>
                <div class="controls">
                    <s:text name="container.location"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.infrastructure.Container.capacity" class="control-label"/>
                <div class="controls">
                    <s:text name="container.capacity"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.infrastructure.Container.tempMin" class="control-label"/>
                <div class="controls">
                    <s:text name="container.tempMin"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.infrastructure.Container.tempMax" class="control-label"/>
                <div class="controls">
                    <s:text name="container.tempMax"/>
                </div>
            </div>

            <div class="form-actions">
            <s:submit name="createContainer" class="btn btn-primary">
                <s:param name="biobankId" value="${infrastructureBean.biobankId}"/>
            </s:submit>
            </div>

        </s:form>

    </s:layout-component>
</s:layout-render>