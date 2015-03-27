<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:set var="pagination" value="${actionBean.samplePagination}"/>

<s:layout-render name="${component.layout.content}">

    <s:layout-component name="body">

        <s:layout-render name="${component.menu.patient}" active="samples"/>

        <table class="table table-hover table-striped">
            <s:layout-render name="${component.sortableHeader.sample}" pagination="${samplePagination}"/>

            <tbody>
            <s:layout-render name="${component.table.emptyTable}" collection="${samplePagination.myPageList}"/>
            <c:forEach var="item" items="${samplePagination.myPageList}">
                <tr>
                    <s:layout-render name="${component.row.sample}" item="${item}"/>
                    <td class="action">
                                       <span class="pull-right">
                                               <div class="tableAction">
                                                   <s:link beanclass="cz.bbmri.action.SampleActionBean" event="detail"
                                                           class="btn btn-info btnMargin">
                                                       <s:param name="id" value="${item.id}"/>
                                                       <f:message key="detail"/>
                                                   </s:link>
                                               </div>
                                       </span>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <%--show pagination only if list contains some data--%>
        <c:if test="${not empty samplePagination.myPageList}">
            <s:layout-render name="${component.pager}" pagination="${samplePagination}"/>
        </c:if>

    </s:layout-component>

</s:layout-render>