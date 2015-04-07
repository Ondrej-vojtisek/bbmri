<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%--<format:message key="cz.bbmri.action.project.ProjectActionBean.detail" var="title"/>--%>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="attachments">

    <stripes:layout-component name="body">


        <security:allowed event="addAttachment">
                   <div class="form-actions">

                       <stripes:link beanclass="cz.bbmri.action.project.ProjectAttachmentsActionBean"
                               event="addAttachment"
                               class="btn btn-primary">
                           <stripes:param name="projectId" value="${actionBean.projectId}"/>
                           <format:message key="cz.bbmri.action.project.ProjectActionBean.addAttachment"/>
                       </stripes:link>

                   </div>
               </security:allowed>

        <stripes:layout-render name="/webpages/project/detail/attachmentTable.jsp"/>


    </stripes:layout-component>
</stripes:layout-render>