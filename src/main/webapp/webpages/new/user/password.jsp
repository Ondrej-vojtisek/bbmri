<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<s:layout-render name="${component.layout.content}"
                 primarymenu="user">

    <s:layout-component name="body">

        <s:layout-render name="${component.menu.user}" active="password"/>

        <fieldset>
            <s:form beanclass="cz.bbmri.action.UserActionBean" class="form-horizontal">

                <div class="control-group">
                    <s:label for="cz.bbmri.entities.User.password" class="control-label"/>
                    <div class="controls">
                        <s:text name="password"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entities.User.password2" class="control-label"/>
                    <div class="controls">
                        <s:text name="password2"/>
                    </div>
                </div>

                <div class="form-actions">
                    <s:submit name="changePassword" class="btn btn-primary"/>
                </div>
            </s:form>

        </fieldset>

    </s:layout-component>
</s:layout-render>