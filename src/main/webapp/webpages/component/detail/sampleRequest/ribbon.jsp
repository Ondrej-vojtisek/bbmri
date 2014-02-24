<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <fieldset>
           <legend><f:message key="cz.bbmri.entities.SampleRequest.sampleRequest"/></legend>
    <table class="table table-striped">
        <s:layout-render name="/webpages/component/detail/sampleRequest/header.jsp"/>
        <tbody>
        <tr>
            <s:layout-render name="/webpages/component/detail/sampleRequest/row.jsp" sampleRequest="${sampleRequest}"/>
        </tr>
        </tbody>
    </table>
    </fieldset>

</s:layout-definition>


