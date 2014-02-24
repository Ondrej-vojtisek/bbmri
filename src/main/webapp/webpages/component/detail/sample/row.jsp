<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${sample.sampleIdentificator.sampleId}</td>
    <td>${sample.sampleIdentificator.year}</td>
    <td>${sample.sampleIdentificator.number}</td>
    <td>${sample.takingDate}</td>
    <td>${sample.materialType}</td>
    <td>${sample.sampleNos.availableSamplesNo}</td>
    <td>${sample.sampleNos.samplesNo}</td>

</s:layout-definition>