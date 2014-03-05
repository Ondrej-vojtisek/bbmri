<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${sample.sampleIdentification.sampleId}</td>
    <td>${sample.sampleIdentification.year}</td>
    <td>${sample.sampleIdentification.number}</td>
    <td><s:layout-render name="/webpages/component/detail/date/date.jsp" date="${sample.takingDate}"/></td>
    <td>${sample.materialType}</td>
    <td>${sample.sampleNos.availableSamplesNo}</td>
    <td>${sample.sampleNos.samplesNo}</td>

</s:layout-definition>