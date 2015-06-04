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
                    <td class="meta-head"><format:message key="id"/></td>
                    <td><textarea>000123</textarea></td>
                </tr>
                <tr>
                    <td class="meta-head"><format:message key="cz.bbmri.entity.Project.name"/></td>
                    <td><textarea>000123</textarea></td>
                </tr>
                <tr>
                    <td class="meta-head"><format:message key="cz.bbmri.entity.Project.approvedBy"/></td>
                    <td><textarea>000123</textarea></td>
                </tr>
                <tr>
                    <td class="meta-head"><format:message key="cz.bbmri.entity.Project.fundingOrganization"/></td>
                    <td><textarea>000123</textarea></td>
                </tr>
                <tr>
                    <td class="meta-head"><format:message key="cz.bbmri.entity.Project.principalInvestigator"/></td>
                    <td><textarea>000123</textarea></td>
                </tr>
                <tr>
                    <td class="meta-head"><format:message key="cz.bbmri.entity.Project.approvalDate"/></td>
                    <td><textarea>000123</textarea></td>
                </tr>


                </tbody>
            </table>
        </core:if>

    </div>

    <table id="items">

        <tbody>
        <tr>
            <th>Item</th>
            <th>Description</th>
            <th>Unit Cost</th>
            <th>Quantity</th>
            <th>Price</th>
        </tr>

        </tbody>
    </table>

    <div id="terms">
        <h5>Terms</h5>
        <textarea>Terms of usage, ...</textarea>
    </div>

</div>


</body>
</html>