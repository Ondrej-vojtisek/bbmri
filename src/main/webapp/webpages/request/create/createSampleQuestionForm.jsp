<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

        <div class="control-group">
            <stripes:label for="cz.bbmri.entities.SampleQuestion.biobank.create" class="control-label"/>

            <div class="controls">
                <stripes:select name="biobankId">
                    <stripes:options-collection collection="${actionBean.allBiobanks}"
                                          label="name" value="id"/>
                </stripes:select>
            </div>
        </div>

        <stripes:label for="cz.bbmri.entities.SampleQuestion.specification.create"/>
        <stripes:textarea name="sampleQuestion.specification"/>

</stripes:layout-definition>
