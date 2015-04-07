<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.action.user.UserActionBean.create" var="title"/>
<stripes:useActionBean var="userBean" beanclass="cz.bbmri.action.user.UserActionBean"/>

<stripes:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="user"
                 secondarymenu="user_create">

    <stripes:layout-component name="body">
        <stripes:form beanclass="cz.bbmri.action.user.UserActionBean" class="form-horizontal">

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.User.name" class="control-label"/>
                <div class="controls">
                    <stripes:text name="user.name"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.User.surname" class="control-label"/>
                <div class="controls">
                    <stripes:text name="user.surname"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.User.password" class="control-label"/>
                <div class="controls">
                    <stripes:text name="user.password"/>
                </div>
            </div>

            <div class="form-actions">
                <stripes:submit name="create" class="btn btn-primary"/>
            </div>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>