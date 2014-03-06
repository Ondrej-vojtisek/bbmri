<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

        <c:if test="${actionBean.desc}">
            <i class="icon-chevron-down"></i>
        </c:if>
        <c:if test="${not actionBean.desc}">
            <i class="icon-chevron-up"></i>
        </c:if>

</s:layout-definition>