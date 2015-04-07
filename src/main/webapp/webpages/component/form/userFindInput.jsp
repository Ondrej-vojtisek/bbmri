<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.User.name" class="control-label"/>
        <div class="controls">
            <stripes:text name="userFind.name"/>
        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.User.surname" class="control-label"/>
        <div class="controls">
            <stripes:text name="userFind.surname"/>
        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.User.email" class="control-label"/>
        <div class="controls">
            <stripes:text name="userFind.email"/>
        </div>
    </div>

</stripes:layout-definition>