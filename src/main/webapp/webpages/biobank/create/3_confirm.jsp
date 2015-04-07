<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.action.biobank.BiobankActionBean.create" var="title"/>

<stripes:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 secondarymenu="biobank_create">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.biobank.CreateActionBean" class="form-horizontal">

            <fieldset>

                <stripes:hidden name="project.id"/>
                <legend><format:message key="cz.bbmri.action.biobank.CreateActionBean.thirdStep"/></legend>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Biobank.abbreviation" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="biobank.abbreviation" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Biobank.name" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="biobank.name" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Biobank.city" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="biobank.city" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Biobank.street" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="biobank.street" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Biobank.newAdministrator" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="newAdministrator.wholeName" readonly="true"/>
                    </div>
                </div>

                <div class="form-actions">
                    <stripes:submit name="confirmStep3" class="btn btn-primary btnMargin"/>
                    <stripes:submit name="backFromStep3" class="btn btn-inverse"/>
                </div>

            </fieldset>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>