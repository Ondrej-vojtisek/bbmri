<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.sample.DiagnosisMaterial.diagnosis" class="control-label"/>
        <div class="controls">
            <stripes:text name="diagnosisMaterial.diagnosis.classification"/>
        </div>
    </div>

</stripes:layout-definition>