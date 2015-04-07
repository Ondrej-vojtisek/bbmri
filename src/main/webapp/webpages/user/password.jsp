<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="user"
                 ternarymenu="password">

    <stripes:layout-component name="body">

        <fieldset>
            <stripes:form beanclass="cz.bbmri.action.user.UserActionBean" class="form-horizontal">

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.User.password" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="password"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.User.password2" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="password2"/>
                    </div>
                </div>

                <div class="form-actions">
                    <stripes:submit name="changePassword" class="btn btn-primary"/>
                </div>
            </stripes:form>

        </fieldset>

    </stripes:layout-component>
</stripes:layout-render>