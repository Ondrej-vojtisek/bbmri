<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="samples"
                 secondarymenu="sample_create">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.sample.CreateSampleActionBean" class="form-horizontal">

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Sample.sampleIdentificator.year" class="control-label"/>
                <div class="controls">
                    <s:text name="sample.sampleIdentificator.year"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Sample.sampleIdentificator.number" class="control-label"/>
                <div class="controls">
                    <s:text name="sample.sampleIdentificator.number"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Sample.sampleIdentificator.sampleId" class="control-label"/>
                <div class="controls">
                    <s:text name="sample.sampleIdentificator.sampleId"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Sample.tissueType.classification" class="control-label"/>
                <div class="controls">
                    <s:text name="sample.tissueType.classification"/>
                </div>
            </div>


            <div class="form-actions">
                <s:submit name="createTissue" class="btn btn-primary"/>
            </div>
        </s:form>


    </s:layout-component>
</s:layout-render>