<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="support"
                 secondarymenu="settings">

    <stripes:layout-component name="body">

        <fieldset>
            <legend><format:message key="cz.bbmri.action.globalSettings.GlobalSettingsActionBean.validity"/></legend>

            <div class="form-actions">

                <stripes:form beanclass="cz.bbmri.action.globalSettings.GlobalSettingsActionBean" class="form-inline">
                    <div class="controls">
                        <stripes:text name="validity.value" class="btnMargin"/>
                        <stripes:submit name="saveValidity" class="btn btn-primary"/>
                    </div>
                </stripes:form>

            </div>

        </fieldset>

    </stripes:layout-component>
</stripes:layout-render>