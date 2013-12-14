<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.user.UserActionBean.detail" var="title"/>
<s:useActionBean var="userBean" beanclass="bbmri.action.user.UserActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="user"
                 secondarymenu="user_all"
                 ternarymenu="password">

    <s:layout-component name="body">

        <fieldset>
            <s:form beanclass="bbmri.action.user.UserActionBean" class="form-horizontal">

                <div class="control-group">
                    <s:label for="password" class="control-label"/>
                    <div class="controls">
                        <s:text id="password" name="password"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="password" class="control-label"/>
                    <div class="controls">
                        <s:text id="password" name="password2"/>
                    </div>
                </div>

                <div class="form-actions">
                    <s:submit name="changePassword" class="btn btn-primary"/>
                </div>
            </s:form>

        </fieldset>

    </s:layout-component>
</s:layout-render>