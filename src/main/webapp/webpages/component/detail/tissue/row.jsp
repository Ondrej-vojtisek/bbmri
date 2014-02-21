<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

                <td>${tissue.tnm.classification}</td>
                <td>${tissue.ptnm.classification}</td>
                <td>${tissue.morphology.classification}</td>
                <td>${tissue.morphology.grading}</td>
                <td>${tissue.freezeDate}</td>
                <td>${tissue.sampleNos.availableSamplesNo}</td>
                <td>${tissue.sampleNos.samplesNo}</td>

</s:layout-definition>
