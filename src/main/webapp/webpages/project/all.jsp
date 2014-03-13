<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.project.ProjectActionBean.allProjects" var="title"/>
<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_all">

    <s:layout-component name="body">


        <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                         pagination="${actionBean.pagination}"
                         componentManager="${actionBean.componentManager}"
                         targetBean="cz.bbmri.action.project.ProjectActionBean"
                         eventName="detail"
                         paramName="projectId"/>

    </s:layout-component>
</s:layout-render>