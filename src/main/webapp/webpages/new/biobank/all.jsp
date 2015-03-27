<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="${component.layout.content}"
                 primarymenu="biobank"
                 secondarymenu="biobank_all">

    <s:layout-component name="body">

        <table class="table table-hover table-striped">
            <s:layout-render name="${component.sortableHeader.biobank}" pagination="${actionBean.pagination}"/>

            <tbody>
            <s:layout-render name="${component.table.emptyTable}" collection="${actionBean.pagination.myPageList}"/>
            <c:forEach var="item" items="${actionBean.pagination.myPageList}">
                <tr>
                    <s:layout-render name="${component.row.biobank}" item="${item}"/>
                    <td class="action">
                                       <span class="pull-right">
                                               <div class="tableAction">
                                                   <s:link beanclass="cz.bbmri.action.BiobankActionBean" event="detail"
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
        <c:if test="${not empty actionBean.pagination.myPageList}">
            <s:layout-render name="${component.pager}" pagination="${actionBean.pagination}"/>
        </c:if>

    </s:layout-component>

</s:layout-render>