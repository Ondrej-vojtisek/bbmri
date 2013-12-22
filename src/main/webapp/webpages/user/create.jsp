<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.user.UserActionBean.create" var="title"/>
<s:useActionBean var="userBean" beanclass="cz.bbmri.action.user.UserActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="user"
                 secondarymenu="user_create">

    <s:layout-component name="body">
        <s:form beanclass="cz.bbmri.action.user.UserActionBean" class="form-horizontal">

            <div class="control-group">
                <s:label for="cz.bbmri.entities.User.name" class="control-label"/>
                <div class="controls">
                    <s:text name="user.name"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.User.surname" class="control-label"/>
                <div class="controls">
                    <s:text name="user.surname"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.User.password" class="control-label"/>
                <div class="controls">
                    <s:text name="user.password"/>
                </div>
            </div>

            <div class="form-actions">
                <s:submit name="create" class="btn btn-primary"/>
            </div>
        </s:form>
    </s:layout-component>
</s:layout-render>