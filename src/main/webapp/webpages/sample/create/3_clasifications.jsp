<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="samples">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/component/detail/patient/ribbon.jsp" patient="${actionBean.patient}"/>
        <s:form beanclass="cz.bbmri.action.sample.CreateSampleActionBean" class="form-horizontal">

            <c:if test="${actionBean.isTissue}">
                <s:layout-render name="/webpages/sample/create/createTissue.jsp"/>
            </c:if>

            <c:if test="${actionBean.isGenome}">
                <s:layout-render name="/webpages/sample/create/createGenome.jsp"/>
            </c:if>

            <c:if test="${actionBean.isDiagnosisMaterial}">
                <s:layout-render name="/webpages/sample/create/createDiagnosisMaterial.jsp"/>
            </c:if>

            <c:if test="${actionBean.isSerum}">
                <s:layout-render name="/webpages/sample/create/createSerum.jsp"/>
            </c:if>

            <div class="form-actions">
                <s:submit name="confirmStep3" class="btn btn-primary btnMargin"/>
                <s:submit name="backFromStep3" class="btn btn-inverse"/>
            </div>
        </s:form>
    </s:layout-component>

    <s:layout-component name="script">
        <script type="text/javascript">
            $(function () {
                $('#dp').datepicker();
            });
        </script>
    </s:layout-component>

</s:layout-render>