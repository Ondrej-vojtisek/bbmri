<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="samples">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/component/detail/patient/ribbon.jsp" patient="${actionBean.patient}"/>

        <c:if test="${actionBean.module.type eq 'ModuleLTS'}">
            Module LTS
        </c:if>

        <c:if test="${actionBean.module.type eq 'ModuleSTS'}">
            Module STS
        </c:if>
        <s:form beanclass="cz.bbmri.action.sample.CreateSampleActionBean" class="form-horizontal">
            <div class="control-group">
                <s:label for="cz.bbmri.entities.sample.retrieved" class="control-label"/>
                <div class="controls">
                    <s:select name="sampleType">
                        <s:options-enumeration enum="cz.bbmri.entities.enumeration.SampleType"/>
                    </s:select>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.Sample.tissueType" class="control-label"/>
                <div class="controls">
                    <s:text name="sample.materialType.type"/>
                </div>
            </div>

            <div class="form-actions">
                <s:submit name="confirmStep2" class="btn btn-primary btnMargin"/>
                <s:submit name="backFromStep2" class="btn btn-inverse"/>
            </div>

        </s:form>

    </s:layout-component>
</s:layout-render>