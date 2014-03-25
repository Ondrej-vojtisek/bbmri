<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="sample"
                 ternarymenu="projects">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                         pagination="${actionBean.pagination}"
                         componentManager="${actionBean.componentManager}"
                         targetBean="cz.bbmri.action.project.ProjectActionBean"
                         eventName="detail"
                         paramName="projectId"/>

    </s:layout-component>
</s:layout-render>