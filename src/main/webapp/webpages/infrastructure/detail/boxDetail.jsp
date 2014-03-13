<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.allBiobanks" var="title"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <s:layout-component name="body">

        <c:if test="${actionBean.isRackBox}">

            <s:layout-render name="/webpages/component/detail/container/ribbon.jsp"
                             record="${actionBean.rackBox.rack.container}"/>

            <s:layout-render name="/webpages/component/detail/rack/ribbon.jsp"
                             record="${actionBean.rackBox.rack}"/>

            <s:layout-render name="/webpages/component/detail/box/ribbon.jsp"
                             record="${actionBean.rackBox}"/>
        </c:if>

        <fieldset>
            <legend><f:message key="cz.bbmri.entities.infrastructure.Position.positions"/></legend>

            <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                             pagination="${actionBean.pagination}"
                             componentManager="${actionBean.componentManager}"
                             targetBean="cz.bbmri.action.sample.sampleactionbean"
                             eventName="detail"
                             paramName="sampleId"/>
        </fieldset>

    </s:layout-component>
</s:layout-render>