<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <core:if test="${not actionBean.project.hasMta}">

        <div class="alert alert-block alert-error fade in">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <h4 class="alert-heading"><format:message key="warning"/>!</h4>

            <p><format:message key="cz.bbmri.action.ProjectActionBean.mtaNotSpecified"/></p>

            <stripes:link beanclass="cz.bbmri.action.AttachmentActionBean"
                    event="addProjectAttachment">
                <stripes:param name="projectId" value="${actionBean.id}"/>
                <%--Match MTA in AttachmentType--%>
                <stripes:param name="attachmentTypeId" value="3"/>
                <format:message key="cz.bbmri.action.AttachmentActionBean.uploadMTA"/>
            </stripes:link>
        </div>
    </core:if>

</stripes:layout-definition>