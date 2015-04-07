<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <table class="table table-hover table-striped">

        <stripes:layout-render name="${componentManager.sortableHeader}"
                         pagination="${pagination}"/>

        <tbody>

        <stripes:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                         collection="${pagination.myPageList}"/>

        <core:forEach var="item" items="${pagination.myPageList}">
            <tr>
                <stripes:layout-render name="${componentManager.tableRow}" record="${item}"/>
                <td class="action">
                    <span class="pull-right">
                        <core:if test="${not empty targetBean}">
                        <%--fix styles of button--%>
                        <div class="tableAction">

                            <stripes:link beanclass="${targetBean}" event="${eventName}"
                                    class="btn btn-info btnMargin">

                                <%--Identifier of previous object - e.g. biobank--%>
                                <stripes:param name="${pagination.identifierParam}"
                                    value="${pagination.identifier}"/>

                                <%--which parameter to access event--%>
                                <stripes:param name="${paramName}" value="${item.id}"/>
                                <format:message key="detail"/>
                            </stripes:link>

                        </div>
                        </core:if>
                        </span>
                </td>
            </tr>
        </core:forEach>
        </tbody>
    </table>

    <%--show pagination only if list contains some data--%>
    <core:if test="${not empty pagination.myPageList}">

        <stripes:layout-render name="/webpages/component/detail/sortableTable/pagination.jsp"
                     pagination="${pagination}"/>

    </core:if>

</stripes:layout-definition>