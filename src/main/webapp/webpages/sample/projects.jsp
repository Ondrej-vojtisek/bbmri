<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="sample"
                 ternarymenu="projects">

    <stripes:layout-component name="body">

        <stripes:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                         pagination="${actionBean.pagination}"
                         componentManager="${actionBean.componentManager}"
                         targetBean="cz.bbmri.action.project.ProjectActionBean"
                         eventName="detail"
                         paramName="projectId"/>

    </stripes:layout-component>
</stripes:layout-render>