<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<core:set var="questionPagination" value="${actionBean.questionPagination}"/>

<stripes:useActionBean var="questionActionBean" beanclass="cz.bbmri.action.QuestionActionBean"/>

<stripes:layout-render name="${component.layout.content}"
                       primarymenu="biobank">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.biobank}" active="questions"/>

        <table class="table table-hover table-striped">
            <stripes:layout-render name="${component.header.question}" pagination="${questionPagination}"/>

            <tbody>
            <stripes:layout-render name="${component.table.emptyTable}"
                                   collection="${questionPagination.myPageList}"/>
            <core:forEach var="item" items="${questionPagination.myPageList}">
                <tr>
                    <stripes:layout-render name="${component.row.question}" item="${item}"/>
                    <td class="action">
                        <span class="pull-right">
                            <div class="tableAction">
                                <security:allowed bean="questionActionBean" event="detail">
                                    <stripes:link
                                            beanclass="cz.bbmri.action.QuestionActionBean"
                                            event="detail"
                                            class="btn btn-info btnMargin">
                                        <stripes:param name="id" value="${item.id}"/>
                                        <format:message key="detail"/>
                                    </stripes:link>
                                </security:allowed>
                            </div>
                        </span>
                    </td>

                </tr>
            </core:forEach>
            </tbody>
        </table>

        <%--show pagination only if list contains some data--%>
        <core:if test="${not empty questionPagination.myPageList}">
            <stripes:layout-render name="${component.pager}" pagination="${questionPagination}"/>
        </core:if>

    </stripes:layout-component>
</stripes:layout-render>