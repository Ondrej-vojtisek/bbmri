<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.user.UserActionBean.detail" var="title"/>
<s:useActionBean var="userBean" beanclass="bbmri.action.user.UserActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="user"
                 secondarymenu="user_all"
                 ternarymenu="personal_data">

    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.user.UserActionBean" class="form-horizontal">
            <s:hidden name="user.id"/>
            <s:hidden name="user.password"/>

            <c:set var="readonly" value="${true}"/>

            <security:allowed bean="userBean" event="update">
                <c:set var="readonly" value="${false}"/>
            </security:allowed>

            <div class="control-group">
                <s:label for="displayName" class="control-label"/>
                <div class="controls">
                    <s:text id="displayName" name="user.displayName" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="email" class="control-label"/>
                <div class="controls">
                    <s:text id="email" name="user.email" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="organization" class="control-label"/>
                <div class="controls">
                    <s:text id="organization" name="user.organization" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="affiliation" class="control-label"/>
                <div class="controls">
                    <s:text id="affiliation" name="user.affiliation" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="created" class="control-label"/>
                <div class="controls">
                    <s:text id="created" name="user.created" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="lastLogin" class="control-label"/>
                <div class="controls">
                    <s:text id="lastLogin" name="user.lastLogin" readonly="true"/>
                </div>
            </div>

            <security:allowed bean="userBean" event="update">
                <div class="form-actions">
                    <s:submit name="update" class="btn btn-primary"/>
                    <s:param name="id" value="${userBean.id}"/>
                </div>
            </security:allowed>
        </s:form>

    </s:layout-component>
</s:layout-render>