<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="samples">

    <stripes:layout-component name="body">

        <stripes:layout-render name="/webpages/component/detail/patient/ribbon.jsp" record="${actionBean.patient}"/>

        <core:if test="${actionBean.module.type eq 'ModuleLTS'}">
            Module LTS
        </core:if>

        <core:if test="${actionBean.module.type eq 'ModuleSTS'}">
            Module STS
        </core:if>
        <stripes:form beanclass="cz.bbmri.action.sample.CreateSampleActionBean" class="form-horizontal">
            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.sample.retrieved" class="control-label"/>
                <div class="controls">
                    <stripes:select name="sampleType">
                        <stripes:options-enumeration enum="cz.bbmri.entity.enumeration.SampleType"/>
                    </stripes:select>
                </div>
            </div>

            <div class="control-group">
                <stripes:label for="cz.bbmri.entities.Sample.tissueType" class="control-label"/>
                <div class="controls">
                    <stripes:text name="sample.materialType.type"/>
                </div>
            </div>

            <div class="form-actions">
                <stripes:submit name="confirmStep2" class="btn btn-primary btnMargin"/>
                <stripes:submit name="backFromStep2" class="btn btn-inverse"/>
            </div>

        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>