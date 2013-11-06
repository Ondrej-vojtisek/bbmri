<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="project.edit" var="title"/>
<s:useActionBean var="projectBean" beanclass="bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="attachments">

    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="project_files"/></legend>
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th><s:label name="attachment.name"/></th>
                    <th><s:label name="attachment.type"/></th>
                    <th><s:label name="attachment.size"/></th>
                    <th><s:label name="attachment.importance"/></th>
                </tr>
                </thead>

                <tbody>

                <c:if test="${empty projectBean.attachments}">
                    <tr>
                        <td colspan="5"><f:message key="empty"/></td>
                    </tr>
                </c:if>

                <c:forEach items="${projectBean.attachments}" var="attachment" varStatus="loop">
                    <tr>
                        <td><c:out value="${attachment.fileName}"/></td>
                        <td><c:out value="${attachment.contentType}"/></td>
                        <td><c:out value="${attachment.size}"/></td>
                        <td><f:message key="AttachmentType.${attachment.attachmentType}"/></td>
                        <td>
                            <security:allowed bean="projectBean" event="downloadAttachment">
                                <s:link beanclass="bbmri.action.project.ProjectActionBean" event="downloadAttachment">
                                    <s:param name="attachment.id" value="${attachment.id}"/>
                                    <f:message key="download"/></s:link>
                            </security:allowed>

                            <security:allowed bean="projectBean" event="deleteAttachment">
                                <s:form beanclass="${projectBean.name}">
                                <s:submit name="deleteAttachment">
                                    <s:param name="attachment.id" value="${attachment.id}"/>
                                    <s:param name="id" value="${projectBean.id}"/>
                                    <f:message key="delete"/>
                                </s:submit>
                                </s:form>
                            </security:allowed>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </fieldset>

    </s:layout-component>
</s:layout-render>