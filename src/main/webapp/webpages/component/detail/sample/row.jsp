<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${record.sampleIdentification.sampleId}</td>
    <td>${record.sampleIdentification.year}</td>
    <td>${record.sampleIdentification.number}</td>
    <td><s:layout-render name="/webpages/component/detail/date/date.jsp" date="${record.takingDate}"/></td>
    <td>${record.materialType}</td>
    <td>${record.sampleNos.availableSamplesNo}</td>
    <td>${record.sampleNos.samplesNo}</td>

</s:layout-definition>