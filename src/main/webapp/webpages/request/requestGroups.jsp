<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <fieldset>
        <legend></legend>

        <div class="form-actions">
            <s:link beanclass="cz.bbmri.action.request.CreateRequestGroup" event="display"
                    class="btn btn-primary btnMargin">
                <s:param name="sampleRequestId" value="${actionBean.sampleRequestId}"/>
                <s:param name="biobankId" value="${actionBean.biobankId}"/>
                <f:message key="cz.bbmri.action.request.CreateRequestGroup.create"/>
            </s:link>
        </div>

        <table class="table table-hover table-striped">

            <s:layout-render name="/webpages/component/detail/requestGroup/header.jsp"/>

            <tbody>

            <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             colspan="4" collection="${actionBean.requestGroups}"/>

            <c:forEach items="${actionBean.requestGroups}" var="requestGroup">
                <tr>
                    <s:layout-render name="/webpages/component/detail/requestGroup/row.jsp"
                                     requestGroup="${requestGroup}"/>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </fieldset>

</s:layout-definition>
