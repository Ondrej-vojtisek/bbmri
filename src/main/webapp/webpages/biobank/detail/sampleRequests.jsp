<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="sampleRequests">

    <s:layout-component name="body">

        <s:layout-component name="body">

            <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                             pagination="${actionBean.pagination}"
                             componentManager="${actionBean.componentManager}"
                             targetBean="cz.bbmri.action.request.RequestActionBean"
                             eventName="detail"
                             paramName="sampleQuestionId"/>

        </s:layout-component>

    </s:layout-component>
</s:layout-render>
