<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.create" var="title"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 secondarymenu="biobank_create">

    <s:layout-component name="body">
        <s:form beanclass="cz.bbmri.action.biobank.CreateActionBean" class="form-horizontal">
            <fieldset>
                <legend><f:message key="cz.bbmri.action.biobank.CreateActionBean.firstStep"/></legend>

                <div class="control-group">
                    <s:label for="cz.bbmri.entities.Biobank.name" class="control-label"/>
                    <div class="controls">
                        <s:text name="biobank.name"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entities.Biobank.address" class="control-label"/>
                    <div class="controls">
                        <s:text name="biobank.address"/>
                    </div>
                </div>

                <div class="form-actions">
                    <s:submit name="confirmStep1" class="btn btn-primary"/>
                </div>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>