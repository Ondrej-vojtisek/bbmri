<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.detail" var="title"/>
<s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="samples">

    <s:layout-component name="body">

        <%--<s:layout-render name="/webpages/component/samples.jsp" samples="${biobankBean.samples}"/>--%>

        <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                        eventName="detail" paramName="sampleId"/>

    </s:layout-component>
</s:layout-render>
