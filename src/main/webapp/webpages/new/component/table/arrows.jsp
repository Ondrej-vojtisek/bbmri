<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

        <core:if test="${pagination.desc}">
            <i class="icon-chevron-down"></i>
        </core:if>

        <core:if test="${not pagination.desc}">
            <i class="icon-chevron-up"></i>
        </core:if>

</stripes:layout-definition>