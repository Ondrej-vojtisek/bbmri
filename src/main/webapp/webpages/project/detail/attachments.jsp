<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.project.ProjectActionBean.detail" var="title"/>
<s:useActionBean var="projectBean" beanclass="bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="attachments">

    <s:layout-component name="body">

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
                    <td class="narrow20"><c:out value="${attachment.contentType}"/></td>
                    <td><c:out value="${attachment.size}"/></td>
                    <td><f:message key="AttachmentType.${attachment.attachmentType}"/></td>
                    <td class="action">
                        <security:allowed bean="projectBean" event="downloadAttachment">
                            <div class="tableAction">
                                <s:link beanclass="bbmri.action.project.ProjectActionBean"
                                        event="downloadAttachment"
                                        class="btn btn-info btnMargin">
                                    <s:param name="attachment.id" value="${attachment.id}"/>
                                    <f:message key="download"/>
                                </s:link>
                            </div>
                        </security:allowed>

                        <f:message var="question" key="bbmri.action.project.ProjectActionBean.questionDeleteAttachment"/>

                        <security:allowed bean="projectBean" event="deleteAttachment">
                            <s:form beanclass="${projectBean.name}">
                                <s:submit name="deleteAttachment"
                                          class="btn btn-danger"
                                          onclick="return confirm('${question}')">
                                    <s:param name="attachmentId" value="${attachment.id}"/>
                                    <s:param name="id" value="${projectBean.id}"/>
                                </s:submit>
                            </s:form>
                        </security:allowed>

                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </s:layout-component>
</s:layout-render>