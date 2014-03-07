<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${record.tnm.classification}</td>
    <td>${record.ptnm.classification}</td>
    <td>${record.morphology.classification}</td>
    <td>${record.morphology.grading}</td>
    <td><s:layout-render name="/webpages/component/detail/date/date.jsp" date="${record.freezeDate}"/></td>

</s:layout-definition>
