<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <table class="table table-striped">
        <s:layout-render name="/webpages/component/detail/tissue/header.jsp"/>
        <tbody>
        <tr>
            <s:layout-render name="/webpages/component/detail/tissue/row.jsp" tissue="${tissue}"/>
        </tr>
        </tbody>
    </table>

</s:layout-definition>


