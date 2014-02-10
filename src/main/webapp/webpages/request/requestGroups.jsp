<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="requestBean" beanclass="cz.bbmri.action.request.RequestActionBean"/>

<fieldset>
    <legend></legend>

    <%--<div class="form-actions">--%>
        <%--<security:allowed bean="requestBean" event="createRequestGroup">--%>
            <%--<s:submit name="createRequestGroup" class="btn btn-primary btnMargin">--%>
                <%--<s:param name="sampleRequestId" value="${requestBean.sampleRequestId}"/>--%>
            <%--</s:submit>--%>
        <%--</security:allowed>--%>
    <%--</div>--%>

    <table class="table table-hover table-striped">
        <thead>
        <tr>
            <th><f:message key="cz.bbmri.entities.RequestGroup.id"/></th>
            <th><f:message key="cz.bbmri.entities.RequestGroup.lastModification"/></th>
            <th><f:message key="cz.bbmri.entities.RequestGroup.amountOfSamples"/></th>
            <th><f:message key="cz.bbmri.entities.RequestGroup.totalAmountOfAliquotes"/></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty requestBean.requestGroups}">
            <tr>
                <td colspan="4">
                    <f:message key="empty"/>
                </td>
            </tr>
        </c:if>

        <c:forEach items="${requestBean.requestGroups}" var="requestGroup">
            <tr>
                <td>${requestGroup.id}</td>
                <td>${requestGroup.lastModification}</td>
                <td>${requestGroup.amountOfSamples}</td>
                <td>${requestGroup.totalAmountOfAliquotes}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</fieldset>
