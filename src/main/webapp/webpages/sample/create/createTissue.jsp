<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.sample.Tissue.tnm" class="control-label"/>
        <div class="controls">
            <stripes:text name="tissue.tnm.classification"/>
        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.sample.Tissue.ptnm" class="control-label"/>
        <div class="controls">
            <stripes:text name="tissue.ptnm.classification"/>
        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.sample.Tissue.morphology" class="control-label"/>
        <div class="controls">
            <stripes:text name="tissue.morphology.classification"/>
        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.sample.field.Morphology.grading" class="control-label"/>
        <div class="controls">
            <stripes:text name="tissue.morphology.grading"/>
        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entities.sample.Tissue.freezeDate" class="control-label"/>
        <div class="controls">
            <div class="input-append date" id="dp" data-date="1-1-2014" data-date-format="dd-mm-yyyy">
                <stripes:text name="tissue.freezeDate" readonly="true" value=""/>
                <span class="add-on"><i class="icon-calendar"></i></span>
            </div>
        </div>
    </div>

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