<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <div class="control-group">
        <s:label for="cz.bbmri.entities.sample.field.SampleNos.availableSamplesNo" class="control-label"/>
        <div class="controls">
            <s:text name="genome.sampleNos.availableSampleNo"/>
        </div>
    </div>

    <div class="control-group">
        <s:label for="cz.bbmri.entities.sample.field.SampleNos.samplesNo" class="control-label"/>
        <div class="controls">
            <s:text name="genome.sampleNos.samplesNo"/>
        </div>
    </div>

</s:layout-definition>