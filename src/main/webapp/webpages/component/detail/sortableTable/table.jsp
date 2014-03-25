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

                        <c:if test="${not empty targetBean}">
                        <%--fix styles of button--%>
                        <div class="tableAction">
                            <s:link beanclass="${targetBean}" event="${eventName}"
                                    class="btn btn-info btnMargin">


                                <%--which parameter to access event--%>
                                <s:param name="${paramName}" value="${item.id}"/>
                                <f:message key="detail"/>
                            </s:link>
                        </div>
                        </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <s:layout-render name="/webpages/component/detail/sortableTable/pagination.jsp"
                     pagination="${pagination}"/>

</s:layout-definition>