<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <table class="table table-hover table-striped">

        <s:layout-render name="/webpages/component/detail/attachment/header.jsp"/>

        <tbody>

        <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                         collection="${actionBean.project.attachments}"/>

        <c:forEach items="${actionBean.project.attachments}" var="attachment">
            <tr>
                <s:layout-render name="/webpages/component/detail/attachment/row.jsp"
                                 record="${attachment}"/>
                <td class="action">
                    <security:allowed event="downloadAttachment">
                        <div class="tableAction">
                            <s:link beanclass="cz.bbmri.action.project.ProjectAttachmentsActionBean"
                                    event="downloadAttachment"
                                    class="btn btn-info btnMargin">
                                <s:param name="attachment.id" value="${attachment.id}"/>
                                <f:message key="download"/>
                            </s:link>
                        </div>
                    </security:allowed>

                    <f:message var="question" key="cz.bbmri.action.project.ProjectActionBean.questionDeleteAttachment"/>

                    <security:allowed event="deleteAttachment">
                        <s:form beanclass="${actionBean.name}">
                            <div class="tableAction">
                                <s:submit name="deleteAttachment"
                                          class="btn btn-danger"
                                          onclick="return confirm('${question}')">
                                    <s:param name="attachmentId" value="${attachment.id}"/>
                                    <s:param name="projectId" value="${actionBean.projectId}"/>
                                </s:submit>
                            </div>
                        </s:form>
                    </security:allowed>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</s:layout-definition>