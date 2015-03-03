<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <table class="table table-hover table-striped">

        <s:layout-render name="${componentManager.sortableHeader}"
                         pagination="${pagination}"/>

        <tbody>

        <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                         collection="${pagination.myPageList}"/>

        <c:forEach var="item" items="${pagination.myPageList}">
            <tr>
                <s:layout-render name="${componentManager.tableRow}" record="${item}"/>
                <td class="action">
                    <span class="pull-right">
                        <c:if test="${not empty targetBean}">
                        <%--fix styles of button--%>
                        <div class="tableAction">

                            <s:link beanclass="${targetBean}" event="${eventName}"
                                    class="btn btn-info btnMargin">

                                <%--Identifier of previous object - e.g. biobank--%>
                                <s:param name="${pagination.identifierParam}"
                                    value="${pagination.identifier}"/>

                                <%--which parameter to access event--%>
                                <s:param name="${paramName}" value="${item.id}"/>
                                <f:message key="detail"/>
                            </s:link>

                        </div>
                        </c:if>
                        </span>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <%--show pagination only if list contains some data--%>
    <c:if test="${not empty pagination.myPageList}">

        <s:layout-render name="/webpages/component/detail/sortableTable/pagination.jsp"
                     pagination="${pagination}"/>

    </c:if>

</s:layout-definition>