<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%--<f:message key="cz.bbmri.action.project.ProjectActionBean.detail" var="title"/>--%>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="attachments">

    <s:layout-component name="body">

        <%--<s:layout-render name="/webpages/project/detail/attachmentTable.jsp"/>--%>

        <table class="table table-hover table-striped">

            <s:layout-render name="${actionBean.componentManager.sortableHeader}"
                             pagination="${actionBean.pagination}"/>

            <tbody>

            <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             collection="${actionBean.pagination.myPageList}"/>

            <c:forEach var="item" items="${actionBean.pagination.myPageList}">
                <tr>
                    <s:layout-render name="${actionBean.componentManager.tableRow}" record="${item}"/>

                    <td class="action">
                        <security:allowed event="downloadAttachment">
                            <div class="tableAction">
                                <s:link beanclass="cz.bbmri.action.project.ProjectAttachmentsActionBean"
                                        event="downloadAttachment"
                                        class="btn btn-info btnMargin">
                                    <s:param name="attachment.id" value="${item.id}"/>
                                    <f:message key="download"/>
                                </s:link>
                            </div>
                        </security:allowed>

                        <f:message var="question"
                                   key="cz.bbmri.action.project.ProjectActionBean.questionDeleteAttachment"/>

                        <security:allowed event="deleteAttachment">
                            <s:form beanclass="${actionBean.name}">
                                <div class="tableAction">
                                    <s:submit name="deleteAttachment"
                                              class="btn btn-danger"
                                              onclick="return confirm('${question}')">
                                        <s:param name="attachmentId" value="${item.id}"/>
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

        <s:layout-render name="/webpages/component/detail/sortableTable/pagination.jsp"
                         pagination="${actionBean.pagination}"/>


        <security:allowed event="addAttachment">
            <div class="form-actions">

                <s:link beanclass="cz.bbmri.action.project.ProjectAttachmentsActionBean"
                        event="addAttachment"
                        class="btn btn-primary">
                    <s:param name="projectId" value="${actionBean.projectId}"/>
                    <f:message key="cz.bbmri.action.project.ProjectActionBean.addAttachment"/>
                </s:link>

            </div>
        </security:allowed>

    </s:layout-component>
</s:layout-render>