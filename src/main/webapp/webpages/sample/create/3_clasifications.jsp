<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="samples">

    <stripes:layout-component name="body">

        <stripes:layout-render name="/webpages/component/detail/patient/ribbon.jsp" record="${actionBean.patient}"/>
        <stripes:form beanclass="cz.bbmri.action.sample.CreateSampleActionBean" class="form-horizontal">

            <core:if test="${actionBean.isTissue}">
                <stripes:layout-render name="/webpages/sample/create/createTissue.jsp"/>
            </core:if>

            <core:if test="${actionBean.isGenome}">
                <stripes:layout-render name="/webpages/sample/create/createGenome.jsp"/>
            </core:if>

            <core:if test="${actionBean.isDiagnosisMaterial}">
                <stripes:layout-render name="/webpages/sample/create/createDiagnosisMaterial.jsp"/>
            </core:if>

            <core:if test="${actionBean.isSerum}">
                <stripes:layout-render name="/webpages/sample/create/createSerum.jsp"/>
            </core:if>

            <div class="form-actions">
                <stripes:submit name="confirmStep3" class="btn btn-primary btnMargin"/>
                <stripes:submit name="backFromStep3" class="btn btn-inverse"/>
            </div>
        </stripes:form>
    </stripes:layout-component>

    <stripes:layout-component name="script">
        <script type="text/javascript">
            $(function () {
                $('#dp').datepicker();
            });
        </script>
    </stripes:layout-component>

</stripes:layout-render>