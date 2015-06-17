<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <title>Request</title>

    <link rel="stylesheet" type="text/css" href="${context}/css/request_export.css"/>

</head>

<body>

<core:set var="question" value="${actionBean.question}"/>
<core:set var="withdraw" value="${actionBean.withdraw}"/>

<core:set var="isQuestion" value="${not empty question}"/>

<core:if test="${isQuestion}">
    <core:set var="biobank" value="${question.biobank}"/>
</core:if>

<core:if test="${not isQuestion}">
    <core:set var="biobank" value="${withdraw.biobank}"/>
</core:if>


<%-- generalized object of question/withdraw --%>
<core:if test="${isQuestion}">
    <core:set var="requisition" value="${question}"/>
</core:if>

<core:if test="${not isQuestion}">
    <core:set var="requisition" value="${withdraw}"/>
</core:if>


<div id="page-wrap">

    <textarea id="header"><core:if test="${isQuestion}"><format:message
            key="cz.bbmri.entity.Question.question"/></core:if>
        <core:if test="${not isQuestion}"><format:message key="cz.bbmri.entity.Withdraw.withdraw"/></core:if>
    </textarea>

    <div id="identity">
        <div id="address">
            <p><b>${biobank.name}</b></p>
            <core:if test="${not empty biobank.contact}">
                <p>${biobank.contact.street}</p>

                <p>${biobank.contact.city},&nbsp;${biobank.contact.zip}</p>
                <core:if test="${not empty biobank.contact.country}">
                    <p>${biobank.contact.country.name}</p>
                </core:if>

                <p><format:message key="cz.bbmri.entity.Contact.phone"/>:&nbsp;${biobank.contact.phone}</p>
            </core:if>

        </div>
        <div id="logo">
            <img id="image" src="${context}/images/BBMRI_logo_CZ.jpg" alt="logo" width="300px">
        </div>

    </div>

    <div style="clear:both"></div>

    <div id="customer">

        <core:if test="${isQuestion}">
            <table id="meta">
                <tbody>
                <tr>
                    <th class="meta-head"><format:message key="id"/></th>
                    <td>${question.project.id}</td>
                </tr>
                <tr>
                    <th class="meta-head"><format:message key="cz.bbmri.entity.Project.name"/></th>
                    <td>${question.project.name}</td>
                </tr>
                <tr>
                    <th class="meta-head"><format:message key="cz.bbmri.entity.Project.fundingOrganization"/></th>
                    <td>${question.project.fundingOrganization}</td>
                </tr>
                <tr>
                    <th class="meta-head"><format:message key="cz.bbmri.entity.Project.principalInvestigator"/></th>
                    <td>${question.project.principalInvestigator}</td>
                </tr>
                <tr>
                    <th class="meta-head"><format:message key="cz.bbmri.entity.Project.approvalDate"/></th>
                    <td><format:formatDate value="${question.project.approvalDate}" type="both"/></td>
                </tr>
                <tr>
                    <th class="meta-head"><format:message key="cz.bbmri.entity.Question.createdMore"/></th>
                    <td><format:formatDate value="${question.created}" type="both"/></td>
                </tr>
                <tr>
                    <th class="meta-head"><format:message key="cz.bbmri.entity.Question.specification"/></th>
                    <td>${question.specification}</td>
                </tr>

                </tbody>
            </table>
        </core:if>

    </div>

    <table id="items">
        <thead>
        <tr>
            <th><format:message key="cz.bbmri.entity.Patient.patient"/></th>
            <th><format:message key="cz.bbmri.entity.Sample.sample"/></th>
            <th><format:message key="cz.bbmri.entity.Request.number"/></th>
        </tr>
        </thead>

        <tbody>
        <core:forEach var="item" items="${requisition.request}">
            <tr>
                <td>${item.sample.patient.institutionalId}</td>
                <td>${item.sample.institutionalId}</td>
                <td>${item.number}</td>
            </tr>
        </core:forEach>
        </tbody>
    </table>

    <div id="terms">
        <h5>Terms</h5>
        <textarea>Terms of usage, ...</textarea>
    </div>

</div>


</body>
</html>