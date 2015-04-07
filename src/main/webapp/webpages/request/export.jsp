<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%--
  Created by IntelliJ IDEA.
  User: Ori
  Date: 24.2.14
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>

<!DOCTYPE html>
<head>
</head>
<body>

<stripes:layout-render name="/webpages/component/detail/biobank/ribbon.jsp"
                 record="${actionBean.sampleRequest.biobank}"/>

<stripes:layout-render name="/webpages/component/detail/project/ribbon.jsp"
                 record="${actionBean.sampleRequest.project}"/>

<stripes:layout-render name="/webpages/component/detail/sampleQuestion/ribbon.jsp"
                 record="${actionBean.sampleRequest}"/>

<fieldset>
    <legend><format:message key="cz.bbmri.entities.SampleQuestion.sampleRequestSet"/></legend>
    <core:forEach items="${actionBean.requests}" var="request">

        <stripes:layout-render name="/webpages/component/detail/request/ribbon.jsp"
                         record="${request}"/>

        <stripes:layout-render name="/webpages/component/detail/sample/ribbon.jsp"
                         record="${request.sample}"/>

        <core:if test="${not empty sampleRequest}">
            <stripes:layout-render name="/webpages/component/detail/position/ribbon.jsp"
                             record="${sampleRequest}"/>
        </core:if>
    </core:forEach>
</fieldset>

</body>
</html>