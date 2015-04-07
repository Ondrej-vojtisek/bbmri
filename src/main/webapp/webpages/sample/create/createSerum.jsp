<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.sample.field.SampleNos.availableSamplesNo" class="control-label"/>
        <div class="controls">
            <stripes:text name="sample.sampleNos.availableSamplesNo"/>
        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.sample.field.SampleNos.samplesNo" class="control-label"/>
        <div class="controls">
            <stripes:text name="sample.sampleNos.samplesNo"/>
        </div>
    </div>

</stripes:layout-definition>