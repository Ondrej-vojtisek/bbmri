<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean beanclass="cz.bbmri.action.AttachmentActionBean" var="attachmentActionBean"/>

<stripes:layout-render name="${component.layout.content}"
                 primarymenu="biobank">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.biobank}" active="attachments"/>

        <security:allowed event="addBiobankAttachment" bean="attachmentActionBean">
            <div class="form-actions">

                <stripes:link beanclass="cz.bbmri.action.AttachmentActionBean"
                        event="addBiobankAttachment"
                        class="btn btn-primary">
                    <stripes:param name="biobankId" value="${actionBean.id}"/>
                    <format:message key="cz.bbmri.action.AttachmentActionBean.addBiobankAttachment"/>
                </stripes:link>

            </div>
        </security:allowed>

        <table class="table table-hover table-striped">

            <stripes:layout-render name="${component.header.attachment}" pagination="${pagination}"/>

            <tbody>
            <stripes:layout-render name="${component.table.emptyTable}"
                             collection="${actionBean.attachmentPagination.myPageList}"/>

            <core:forEach var="item" items="${actionBean.attachmentPagination.myPageList}">
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
                                   key="cz.bbmri.action.biobank.BiobankAttachmentsActionBean.questionDeleteAttachment"/>

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
                         pagination="${actionBean.attachmentPagination}"/>

    </stripes:layout-component>
</stripes:layout-render>