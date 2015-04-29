<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">

        <table class="table table-hover table-striped">
            <stripes:layout-render name="${component.header.archive}" pagination="${actionBean.pagination}"/>

            <tbody>
            <stripes:layout-render name="${component.table.emptyTable}"
                                   collection="${actionBean.pagination.myPageList}"/>
            <core:forEach var="item" items="${actionBean.pagination.myPageList}">
                <tr>
                    <stripes:layout-render name="${component.row.archive}" item="${item}"/>
                </tr>
            </core:forEach>
            </tbody>
        </table>

        <%--show pagination only if list contains some data--%>
        <core:if test="${not empty actionBean.pagination.myPageList}">
            <stripes:layout-render name="${component.pager}" pagination="${actionBean.pagination}"/>
        </core:if>

    </stripes:layout-component>

</stripes:layout-render>