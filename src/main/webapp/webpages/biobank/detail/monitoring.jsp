<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="monitoring">

    <s:layout-component name="body">
        <div class="form-actions">

            <c:set var="disabled" value="${false}"/>

            <c:if test="${empty actionBean.monitoringList}">
                List is empty
                <%--TODO--%>
                <c:set var="disabled" value="${true}"/>
            </c:if>

            <s:form beanclass="cz.bbmri.action.infrastructure.ContainerActionBean" method="POST" class="form-inline">

                <s:select name="containerId" class="btnMargin">
                    <s:options-collection collection="${actionBean.monitoringList}" value="id" label="name"/>
                </s:select>

                <s:submit name="detail" class="btn btn-info" disabled="${disabled}"/>

            </s:form>
        </div>

    </s:layout-component>
</s:layout-render>