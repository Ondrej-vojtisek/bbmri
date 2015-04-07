<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean beanclass="cz.bbmri.action.AttachmentActionBean" var="attachmentActionBean"/>
<core:set var="attachmentPagination" value="${actionBean.attachmentPagination}"/>

<stripes:layout-render name="${component.layout.content}"
                 primarymenu="project">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.project}" active="attachments"/>

         <%--Set authProjectId of AuthotizationActionBean to enable security tag--%>
        <core:set target="${attachmentActionBean}" property="authProjectId" value="${actionBean.id}"/>

        <security:allowed event="addProjectAttachment" bean="attachmentActionBean">
            <div class="form-actions">

                <stripes:link beanclass="cz.bbmri.action.AttachmentActionBean"
                        event="addProjectAttachment"
                        class="btn btn-primary">
                    <stripes:param name="projectId" value="${actionBean.id}"/>
                    <format:message key="cz.bbmri.action.AttachmentActionBean.addProjectAttachment"/>
                </stripes:link>

            </div>
        </security:allowed>

        <table class="table table-hover table-striped">

            <stripes:layout-render name="${component.header.attachment}" pagination="${attachmentPagination}"/>

            <tbody>
            <stripes:layout-render name="${component.table.emptyTable}"
                             collection="${attachmentPagination.myPageList}"/>

            <core:forEach var="item" items="${attachmentPagination.myPageList}">
                <tr>
                    <stripes:layout-render name="${component.row.attachment}" item="${item}"/>

                    <td class="action">
                        <security:allowed event="download" bean="attachmentActionBean">
                            <div class="tableAction">
                                <stripes:link beanclass="cz.bbmri.action.AttachmentActionBean"
                                        event="download"
                                        class="btn btn-info btnMargin">
                                    <stripes:param name="id" value="${item.id}"/>
                                    <format:message key="cz.bbmri.action.AttachmentActionBean.download"/>
                                </stripes:link>
                            </div>
                        </security:allowed>

                        <format:message var="question"
                                   key="cz.bbmri.action.project.ProjectAttachmentsActionBean.questionDeleteAttachment"/>

                        <security:allowed event="delete" bean="attachmentActionBean">
                            <stripes:form beanclass="${attachmentActionBean.name}">

                                <stripes:hidden name="id" value="${item.id}"/>

                                <div class="tableAction">
                                    <stripes:submit name="delete"
                                              class="btn btn-danger"
                                              onclick="return confirm('${question}')"/>
                                </div>
                            </stripes:form>
                        </security:allowed>
                    </td>

                </tr>
            </core:forEach>
            </tbody>
        </table>

        <stripes:layout-render name="${component.pager}"
                         pagination="${attachmentPagination}"/>

    </stripes:layout-component>
</stripes:layout-render>