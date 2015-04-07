<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <table class="table table-striped">
        <stripes:layout-render name="/webpages/component/detail/genome/header.jsp"/>
        <tbody>
        <tr>
            <stripes:layout-render name="/webpages/component/detail/genome/row.jsp" record="${record}"/>
        </tr>
        </tbody>
    </table>

</stripes:layout-definition>


