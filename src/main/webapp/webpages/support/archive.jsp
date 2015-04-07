<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="/layouts/layout_content.jsp"
                primarymenu="support"
                secondarymenu="archive">

    <stripes:layout-component name="body">

        <stripes:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                               pagination="${actionBean.pagination}"
                               componentManager="${actionBean.componentManager}"/>

    </stripes:layout-component>

</stripes:layout-render>

