<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

        <div class="control-group">
            <s:label for="cz.bbmri.entities.SampleQuestion.biobank.create" class="control-label"/>

            <div class="controls">
                <s:select name="biobankId">
                    <s:options-collection collection="${actionBean.allBiobanks}"
                                          label="name" value="id"/>
                </s:select>
            </div>
        </div>

        <s:label for="cz.bbmri.entities.SampleQuestion.specification.create"/>
        <s:textarea name="sampleQuestion.specification"/>

</s:layout-definition>
