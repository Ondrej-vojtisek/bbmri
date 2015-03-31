<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <c:if test="${not actionBean.project.hasMta}">

        <div class="alert alert-block alert-error fade in">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <h4 class="alert-heading"><f:message key="warning"/>!</h4>

            <p><f:message key="cz.bbmri.action.ProjectActionBean.mtaNotSpecified"/></p>

            <s:link beanclass="cz.bbmri.action.AttachmentActionBean"
                    event="addProjectAttachment">
                <s:param name="projectId" value="${actionBean.id}"/>
                <%--Match MTA in AttachmentType--%>
                <s:param name="attachmentTypeId" value="3"/>
                <f:message key="cz.bbmri.action.AttachmentActionBean.uploadMTA"/>
            </s:link>
        </div>
    </c:if>

</s:layout-definition>