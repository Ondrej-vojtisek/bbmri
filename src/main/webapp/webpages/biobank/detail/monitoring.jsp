<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="monitoring">

    <stripes:layout-component name="body">
        <div class="form-actions">

            <core:set var="disabled" value="${false}"/>

            <core:if test="${empty actionBean.monitoringList}">
                List is empty
                <%--TODO--%>
                <core:set var="disabled" value="${true}"/>
            </core:if>

            <stripes:form beanclass="cz.bbmri.action.infrastructure.ContainerActionBean" method="POST" class="form-inline">

                <stripes:select name="containerId" class="btnMargin">
                    <stripes:options-collection collection="${actionBean.monitoringList}" value="id" label="name"/>
                </stripes:select>

                <stripes:submit name="detail" class="btn btn-info" disabled="${disabled}"/>

            </stripes:form>
        </div>

    </stripes:layout-component>
</stripes:layout-render>