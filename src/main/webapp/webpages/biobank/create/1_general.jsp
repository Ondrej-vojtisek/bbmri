<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 secondarymenu="biobank_create">

    <stripes:layout-component name="body">
        <stripes:form beanclass="cz.bbmri.action.biobank.CreateActionBean" class="form-horizontal">
            <fieldset>
                <legend><format:message key="cz.bbmri.action.biobank.CreateActionBean.firstStep"/></legend>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Biobank.abbreviation" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="biobank.abbreviation"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Biobank.name" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="biobank.name"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Biobank.street" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="biobank.street"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Biobank.city" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="biobank.city"/>
                    </div>
                </div>

                <div class="form-actions">
                    <stripes:submit name="confirmStep1" class="btn btn-primary"/>
                </div>
            </fieldset>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>