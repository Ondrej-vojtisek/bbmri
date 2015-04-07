<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="user"
                 ternarymenu="personal_data">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.user.UserActionBean" class="form-horizontal">
            <stripes:hidden name="user.id"/>
            <stripes:hidden name="user.password"/>

            <core:set var="readonly" value="${true}"/>

            <security:allowed event="update">
                <core:set var="readonly" value="${false}"/>
            </security:allowed>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.User.displayName" class="control-label"/>
                <div class="controls">
                    <stripes:text name="user.displayName" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.User.email" class="control-label"/>
                <div class="controls">
                    <stripes:text name="user.email" readonly="${readonly}"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.User.organization" class="control-label"/>
                <div class="controls">
                    <stripes:text name="user.organization" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.User.affiliation" class="control-label"/>
                <div class="controls">
                    <stripes:text name="user.affiliation" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.User.created" class="control-label"/>
                <div class="controls">
                    <stripes:text name="user.created" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.User.lastLogin" class="control-label"/>
                <div class="controls">
                    <stripes:text name="user.lastLogin" readonly="true"/>
                </div>
            </div>

            <div class="form-actions">
            <security:allowed event="update">

                    <stripes:submit name="update" class="btn btn-primary btnMargin"/>
                    <stripes:param name="userId" value="${actionBean.userId}"/>

            </security:allowed>

                <security:allowed event="remove">
                    <stripes:form beanclass="cz.bbmri.action.user.UserActionBean">
                        <format:message var="question" key="cz.bbmri.action.user.UserActionBean.questionDelete"/>
                        <stripes:submit name="remove" class="btn btn-danger" onclick="return confirm('${question}')"
                                  disabled="true">
                            <stripes:param name="userId" value="${user.id}"/>
                        </stripes:submit>
                    </stripes:form>
                </security:allowed>
            </div>

        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>