<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean beanclass="cz.bbmri.action.AttachmentActionBean" var="attachmentActionBean"/>
<c:set var="attachmentPagination" value="${actionBean.attachmentPagination}"/>

<s:layout-render name="${component.layout.content}"
                 primarymenu="project">

    <s:layout-component name="body">

        <s:layout-render name="${component.menu.project}" active="attachments"/>

        <security:allowed event="addProjectAttachment" bean="attachmentActionBean">
            <div class="form-actions">

                <s:link beanclass="cz.bbmri.action.AttachmentActionBean"
                        event="addProjectAttachment"
                        class="btn btn-primary">
                    <s:param name="projectId" value="${actionBean.id}"/>
                    <f:message key="cz.bbmri.action.AttachmentActionBean.addProjectAttachment"/>
                </s:link>

            </div>
        </security:allowed>

        <table class="table table-hover table-striped">

            <s:layout-render name="${component.sortableHeader.attachment}" pagination="${attachmentPagination}"/>

            <tbody>
            <s:layout-render name="${component.table.emptyTable}"
                             collection="${attachmentPagination.myPageList}"/>

            <c:forEach var="item" items="${attachmentPagination.myPageList}">
                <tr>
                    <s:layout-render name="${component.row.attachment}" item="${item}"/>

                    <td class="action">
                        <security:allowed event="download" bean="attachmentActionBean">
                            <div class="tableAction">
                                <s:link beanclass="cz.bbmri.action.AttachmentActionBean"
                                        event="download"
                                        class="btn btn-info btnMargin">
                                    <s:param name="id" value="${item.id}"/>
                                    <f:message key="cz.bbmri.action.AttachmentActionBean.download"/>
                                </s:link>
                            </div>
                        </security:allowed>

                        <f:message var="question"
                                   key="cz.bbmri.action.project.ProjectAttachmentsActionBean.questionDeleteAttachment"/>

                        <security:allowed event="delete" bean="attachmentActionBean">
                            <s:form beanclass="${attachmentActionBean.name}">

                                <s:hidden name="id" value="${item.id}"/>

                                <div class="tableAction">
                                    <s:submit name="delete"
                                              class="btn btn-danger"
                                              onclick="return confirm('${question}')"/>
                                </div>
                            </s:form>
                        </security:allowed>
                    </td>

                </tr>
            </c:forEach>
            </tbody>
        </table>

        <s:layout-render name="${component.pager}"
                         pagination="${attachmentPagination}"/>

    </s:layout-component>
</s:layout-render>