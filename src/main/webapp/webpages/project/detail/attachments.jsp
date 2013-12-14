<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.project.ProjectActionBean.detail" var="title"/>
<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="attachments">

    <s:layout-component name="body">

        <jsp:include page="/webpages/project/component/attachments.jsp"/>

        <security:allowed bean="projectBean" event="addAttachment">
            <div class="form-actions">

            <s:link beanclass="cz.bbmri.action.project.ProjectActionBean" event="addAttachment" class="btn btn-primary">
                <s:param name="id" value="${projectBean.id}"/>
                <f:message key="cz.bbmri.action.project.ProjectActionBean.addAttachment"/>
            </s:link>

            </div>
        </security:allowed>

    </s:layout-component>
</s:layout-render>