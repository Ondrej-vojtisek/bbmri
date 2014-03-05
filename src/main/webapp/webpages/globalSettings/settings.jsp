<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="/layouts/layout_content.jsp">

    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="cz.bbmri.action.globalSettings.GlobalSettingsActionBean.validity"/></legend>

            <div class="form-actions">

                <s:form beanclass="cz.bbmri.action.globalSettings.GlobalSettingsActionBean" class="form-horizontal">
                    <div class="controls">
                        <s:text name="validity.value"/>
                    </div>

                    <s:submit name="saveValidity" class="btn btn-primary"/>
                </s:form>

            </div>

        </fieldset>

    </s:layout-component>
</s:layout-render>