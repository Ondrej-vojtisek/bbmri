<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <table class="table table-hover table-striped">

        <stripes:layout-render name="${actionBean.componentManager.sortableHeader}"
                         pagination="${actionBean.pagination}"/>

        <tbody>

        <stripes:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                         collection="${actionBean.pagination.myPageList}"/>

        <core:forEach var="item" items="${actionBean.pagination.myPageList}">
            <tr>
                <stripes:layout-render name="${actionBean.componentManager.tableRow}" record="${item}"/>

                <td class="action">
                    <span class="pull-right">
                    <security:allowed event="downloadAttachment">
                        <div class="tableAction">
                            <stripes:link beanclass="cz.bbmri.action.biobank.BiobankAttachmentsActionBean"
                                    event="downloadAttachment"
                                    class="btn btn-info btnMargin">
                                <stripes:param name="biobankAttachment.id" value="${item.id}"/>
                                <format:message key="download"/>
                            </stripes:link>
                        </div>
                    </security:allowed>

                    <format:message var="question"
                               key="cz.bbmri.action.biobank.BiobankAttachmentsActionBean.questionDeleteAttachment"/>

                    <security:allowed event="deleteAttachment">
                        <stripes:form beanclass="${actionBean.name}">

                            <stripes:hidden name="attachmentId" value="${item.id}"/>
                            <stripes:hidden name="biobankd"/>


                            <div class="tableAction">
                                <stripes:submit name="deleteAttachment"
                                          class="btn btn-danger"
                                          onclick="return confirm('${question}')"/>
                            </div>
                        </stripes:form>
                    </security:allowed>
                    </span>
                </td>

            </tr>
        </core:forEach>
        </tbody>
    </table>

    <stripes:layout-render name="/webpages/component/detail/sortableTable/pagination.jsp"
                     pagination="${actionBean.pagination}"/>

</stripes:layout-definition>