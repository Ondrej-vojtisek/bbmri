<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<core:set var="pagination" value="${actionBean.questionPagination}"/>

<stripes:layout-render name="${component.layout.content}"
                 primarymenu="biobank">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.biobank}" active="questions"/>

        <table class="table table-hover table-striped">
            <stripes:layout-render name="${component.header.question}" pagination="${pagination}"/>

            <tbody>
            <stripes:layout-render name="${component.table.emptyTable}"
                             collection="${pagination.myPageList}"/>
            <core:forEach var="item" items="${pagination.myPageList}">
                <tr>
                    <stripes:layout-render name="${component.row.question}" item="${item}"/>
                </tr>
            </core:forEach>          l
            </tbody>
        </table>

        <%--show pagination only if list contains some data--%>
        <core:if test="${not empty pagination.myPageList}">
            <stripes:layout-render name="${component.pager}" pagination="${pagination}"/>
        </core:if>

    </stripes:layout-component>
</stripes:layout-render>