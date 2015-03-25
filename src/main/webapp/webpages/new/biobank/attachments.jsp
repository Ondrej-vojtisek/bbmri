<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean beanclass="cz.bbmri.action.AttachmentActionBean" var="attachmentActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="attachments">

    <s:layout-component name="body">

        <security:allowed event="addBiobankAttachment" bean="attachmentActionBean">
            <div class="form-actions">

                <s:link beanclass="cz.bbmri.action.AttachmentActionBean"
                        event="addBiobankAttachment"
                        class="btn btn-primary">
                    <s:param name="biobankId" value="${actionBean.id}"/>
                    <f:message key="cz.bbmri.action.AttachmentActionBean.addBiobankAttachment"/>
                </s:link>

            </div>
        </security:allowed>

        <table class="table table-hover table-striped">

            <thead>
            <tr>
                <th>
                    <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                                     msgKey="cz.bbmri.entity.Attachment.name"
                                     column="fileName"
                                     pagination="${actionBean.attachmentPagination}"/>
                </th>
                <th>
                    <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                                     msgKey="cz.bbmri.entity.Attachment.unit"
                                     column="size"
                                     pagination="${actionBean.attachmentPagination}"/>
                </th>

                <th>
                    <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                                     msgKey="cz.bbmri.entity.Attachment.importance"
                                     column="biobankAttachmentType"
                                     pagination="${actionBean.attachmentPagination}"/>
                </th>
            </tr>
            </thead>

            <tbody>

            <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             collection="${actionBean.attachmentPagination.myPageList}"/>

            <c:forEach var="item" items="${actionBean.attachmentPagination.myPageList}">
                <tr>
                    <td>${item.fileName}</td>
                    <td>${item.size}</td>
                    <td><f:message
                            key="cz.bbmri.entity.AttachmentType.${item.attachmentType.name}"/></td>

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
                                     key="cz.bbmri.action.biobank.BiobankAttachmentsActionBean.questionDeleteAttachment"/>

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

        <s:layout-render name="/webpages/component/detail/sortableTable/pagination.jsp"
                         pagination="${actionBean.attachmentPagination}"/>

    </s:layout-component>
</s:layout-render>