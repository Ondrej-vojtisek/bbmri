<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%--<f:message key="cz.bbmri.action.project.ProjectActionBean.detail" var="title"/>--%>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="attachments">

    <s:layout-component name="body">


        <security:allowed event="addAttachment">
                   <div class="form-actions">

                       <s:link beanclass="cz.bbmri.action.project.ProjectAttachmentsActionBean"
                               event="addAttachment"
                               class="btn btn-primary">
                           <s:param name="projectId" value="${actionBean.projectId}"/>
                           <f:message key="cz.bbmri.action.project.ProjectActionBean.addAttachment"/>
                       </s:link>

                   </div>
               </security:allowed>

        <s:layout-render name="/webpages/project/detail/attachmentTable.jsp"/>


    </s:layout-component>
</s:layout-render>