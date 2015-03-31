<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:set var="pagination" value="${actionBean.withdrawPagination}"/>

<s:layout-render name="${component.layout.content}"
                 primarymenu="biobank">

    <s:layout-component name="body">

        <s:layout-render name="${component.menu.biobank}" active="withdraws"/>

        <table class="table table-hover table-striped">
            <s:layout-render name="${component.sortableHeader.withdraw}" pagination="${pagination}"/>

            <tbody>
            <s:layout-render name="${component.table.emptyTable}"
                             collection="${pagination.myPageList}"/>
            <c:forEach var="item" items="${pagination.myPageList}">
                <tr>
                    <s:layout-render name="${component.row.withdraw}" item="${item}"/>
                        <%--<td class="action">--%>
                        <%--<span class="pull-right">--%>
                        <%--<div class="tableAction">--%>
                        <%--<s:link beanclass="cz.bbmri.action.PatientActionBean" event="detail"--%>
                        <%--class="btn btn-info btnMargin">--%>
                        <%--<s:param name="id" value="${item.id}"/>--%>
                        <%--<f:message key="detail"/>--%>
                        <%--</s:link>--%>
                        <%--</div>--%>
                        <%--</span>--%>
                        <%--</td>--%>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <%--show pagination only if list contains some data--%>
        <c:if test="${not empty pagination.myPageList}">
            <s:layout-render name="${component.pager}" pagination="${pagination}"/>
        </c:if>

    </s:layout-component>
</s:layout-render>