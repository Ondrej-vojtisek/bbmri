<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.allBiobanks" var="title"/>
<s:useActionBean var="infrastructureBean" beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/component/detail/container/ribbon.jsp"
                         container="${infrastructureBean.container}"/>

        <s:layout-render name="/webpages/component/detail/rack/ribbon.jsp"
                         rack="${infrastructureBean.rack}"/>

        <s:layout-render name="/webpages/component/detail/box/ribbon.jsp"
                         rack="${infrastructureBean.box}"/>

        <c:if test="${infrastructureBean.box.type eq 'RackBox'}">
            Rack box
        </c:if>

        <c:if test="${infrastructureBean.box.type eq 'StandaloneBox'}">
            StandaloneBox
        </c:if>

        <fieldset>
               <legend><f:message key="cz.bbmri.entities.infrastructure.Position.positions"/></legend>

        <s:layout-render name="/webpages/infrastructure/component/positions.jsp"
                         boxes="${infrastructureBean.positions}"/>

        </fieldset>

    </s:layout-component>
</s:layout-render>