<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${tissue.tnm.classification}</td>
    <td>${tissue.ptnm.classification}</td>
    <td>${tissue.morphology.classification}</td>
    <td>${tissue.morphology.grading}</td>
    <td><s:layout-render name="/webpages/component/detail/date/date.jsp" date="${tissue.freezeDate}"/></td>

</s:layout-definition>
