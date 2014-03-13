<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.sample.SampleActionBean.detail" var="title"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="sample"
                 ternarymenu="reservations">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                         pagination="${actionBean.pagination}"
                         componentManager="${actionBean.componentManager}"
                         targetBean="cz.bbmri.action.request.RequestActionBean"
                         eventName="detail"
                         paramName="sampleQuestionId"/>


    </s:layout-component>
</s:layout-render>