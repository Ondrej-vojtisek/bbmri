<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="support"
                 secondarymenu="settings">

    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="cz.bbmri.action.globalSettings.GlobalSettingsActionBean.validity"/></legend>

            <div class="form-actions">

                <s:form beanclass="cz.bbmri.action.globalSettings.GlobalSettingsActionBean" class="form-inline">
                    <div class="controls">
                        <s:text name="validity.value" class="btnMargin"/>
                        <s:submit name="saveValidity" class="btn btn-primary"/>
                    </div>
                </s:form>

            </div>

        </fieldset>

    </s:layout-component>
</s:layout-render>