<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.allBiobanks" var="title"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.infrastructure.BoxActionBean" class="form-horizontal">

            <s:layout-render name="/webpages/infrastructure/component/createBox.jsp"
                             boxType="standaloneBox"/>

            <s:hidden name="biobankId"/>

            <div class="form-actions">
                <s:submit name="createStandaloneBox" class="btn btn-primary"/>
            </div>

        </s:form>

    </s:layout-component>
</s:layout-render>