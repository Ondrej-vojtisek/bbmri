<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <fieldset>
           <legend><f:message key="cz.bbmri.entities.Biobank.biobank"/></legend>
    <table class="table table-striped">
        <s:layout-render name="/webpages/component/detail/biobank/header.jsp"/>
        <tbody>
        <tr>
            <s:layout-render name="/webpages/component/detail/biobank/row.jsp" biobank="${biobank}"/>
        </tr>
        </tbody>
    </table>
    </fieldset>
</s:layout-definition>


