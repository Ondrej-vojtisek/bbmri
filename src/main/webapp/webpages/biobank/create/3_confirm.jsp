<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.create" var="title"/>
<s:useActionBean var="biobankCreateBean" beanclass="cz.bbmri.action.biobank.CreateActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 secondarymenu="biobank_create">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.biobank.CreateActionBean" class="form-horizontal">

            <fieldset>

                <s:hidden name="project.id"/>
                <legend><f:message key="cz.bbmri.action.biobank.CreateActionBean.thirdStep"/></legend>

                <div class="control-group">
                    <s:label for="name" class="control-label"/>
                    <div class="controls">
                        <s:text id="name" name="biobank.name" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="address" class="control-label"/>
                    <div class="controls">
                        <s:text id="address" name="biobank.address" readonly="true"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="newAdministrator" class="control-label"/>
                    <div class="controls">
                        <s:text id="newAdministrator" name="newAdministrator.wholeName" readonly="true"/>
                    </div>
                </div>

                <div class="form-actions">
                    <s:submit name="confirmStep3" class="btn btn-primary btnMargin"/>
                    <s:submit name="backFromStep3" class="btn btn-inverse"/>
                </div>

            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>