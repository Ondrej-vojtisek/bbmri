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

<s:layout-render name="/webpages/component/detail/biobank/ribbon.jsp"
                 biobank="${actionBean.sampleRequest.biobank}"/>

<s:layout-render name="/webpages/component/detail/project/ribbon.jsp"
                 project="${actionBean.sampleRequest.project}"/>

<s:layout-render name="/webpages/component/detail/sampleQuestion/ribbon.jsp"
                 sampleQuestion="${actionBean.sampleRequest}"/>

<fieldset>
    <legend><f:message key="cz.bbmri.entities.SampleQuestion.sampleRequestSet"/></legend>
    <c:forEach items="${actionBean.requests}" var="request">

        <s:layout-render name="/webpages/component/detail/request/ribbon.jsp"
                         request="${request}"/>

        <s:layout-render name="/webpages/component/detail/sample/ribbon.jsp"
                         sample="${request.sample}"/>

        <c:if test="${not empty sampleRequest}">
            <s:layout-render name="/webpages/component/detail/position/ribbon.jsp"
                             position="${sampleRequest}"/>
        </c:if>
    </c:forEach>
</fieldset>

</body>
</html>