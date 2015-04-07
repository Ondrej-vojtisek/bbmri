<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.infrastructure.BoxActionBean" class="form-horizontal">

            <stripes:layout-render name="/webpages/infrastructure/component/createBox.jsp"
                             boxType="standaloneBox"/>

            <stripes:hidden name="biobankId"/>

            <div class="form-actions">
                <stripes:submit name="createStandaloneBox" class="btn btn-primary"/>
            </div>

        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>