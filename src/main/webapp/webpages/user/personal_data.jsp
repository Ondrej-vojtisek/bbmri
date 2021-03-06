<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="user"
                 ternarymenu="personal_data">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.user.UserActionBean" class="form-horizontal">
            <s:hidden name="user.id"/>
            <s:hidden name="user.password"/>

            <c:set var="readonly" value="${true}"/>

            <security:allowed event="update">
                <c:set var="readonly" value="${false}"/>
            </security:allowed>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.User.displayName" class="control-label"/>
                <div class="controls">
                    <s:text name="user.displayName" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.User.email" class="control-label"/>
                <div class="controls">
                    <s:text name="user.email" readonly="${readonly}"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.User.organization" class="control-label"/>
                <div class="controls">
                    <s:text name="user.organization" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.User.affiliation" class="control-label"/>
                <div class="controls">
                    <s:text name="user.affiliation" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.User.created" class="control-label"/>
                <div class="controls">
                    <s:text name="user.created" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.User.lastLogin" class="control-label"/>
                <div class="controls">
                    <s:text name="user.lastLogin" readonly="true"/>
                </div>
            </div>

            <div class="form-actions">
            <security:allowed event="update">

                    <s:submit name="update" class="btn btn-primary btnMargin"/>
                    <s:param name="userId" value="${actionBean.userId}"/>

            </security:allowed>

                <security:allowed event="remove">
                    <s:form beanclass="cz.bbmri.action.user.UserActionBean">
                        <f:message var="question" key="cz.bbmri.action.user.UserActionBean.questionDelete"/>
                        <s:submit name="remove" class="btn btn-danger" onclick="return confirm('${question}')"
                                  disabled="true">
                            <s:param name="userId" value="${user.id}"/>
                        </s:submit>
                    </s:form>
                </security:allowed>
            </div>

        </s:form>

    </s:layout-component>
</s:layout-render>