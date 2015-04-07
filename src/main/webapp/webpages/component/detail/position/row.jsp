<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <td>${record.row}</td>
    <td>${record.column}</td>
    <td>${record.sequentialPosition}</td>
    <td>${record.sample.sampleIdentification.sampleId}</td>

</stripes:layout-definition>

