<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <fieldset>
           <%--<legend><f:message key="cz.bbmri.entityroject.project"/></legend>--%>
    <table class="table table-striped">
        <s:layout-render name="/webpages/component/detail/project/header.jsp"/>
        <tbody>
        <tr>
            <s:layout-render name="/webpages/component/detail/project/row.jsp" record="${record}"/>
        </tr>
        </tbody>
    </table>
    </fieldset>

</s:layout-definition>


