<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.action.project.ProjectActionBean.allProjects" var="title"/>
<stripes:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>
<stripes:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_all">

    <stripes:layout-component name="body">


        <stripes:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                         pagination="${actionBean.pagination}"
                         componentManager="${actionBean.componentManager}"
                         targetBean="cz.bbmri.action.project.ProjectActionBean"
                         eventName="detail"
                         paramName="projectId"/>

    </stripes:layout-component>
</stripes:layout-render>