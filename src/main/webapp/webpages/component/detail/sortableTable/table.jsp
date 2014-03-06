<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <table class="table table-hover table-striped">

        <s:layout-render name="${actionBean.componentManager.sortableHeader}"/>

        <tbody>

        <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                         collection="${actionBean.pagination.pageList}"/>

        <c:forEach var="item" items="${actionBean.pagination.pageList}">
            <tr>
                <s:layout-render name="${actionBean.componentManager.tableRow}" record="${item}"/>
                <td class="action">
                    <%--Event must be defined in actionBean--%>
                    <security:allowed event="${eventName}">
                        <%--fix styles of button--%>
                        <div class="tableAction">
                            <s:link beanclass="${actionBean.name}" event="${eventName}"
                                    class="btn btn-info btnMargin">
                                <%--which parameter to access event--%>
                                <s:param name="${paramName}" value="${item.id}"/>
                                <f:message key="detail"/>
                            </s:link>
                        </div>
                    </security:allowed>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <s:layout-render name="/webpages/component/detail/sortableTable/pagination.jsp"/>

</s:layout-definition>