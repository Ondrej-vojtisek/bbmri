<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="biobank.detail" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.biobank.BiobankActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="detail">

    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.biobank.BiobankActionBean">
        <fieldset>
            <legend><f:message key="biobank.detail"/></legend>
            <table>
                <tr>
                    <th><s:label name="biobank.name"/></th>
                    <td><s:text name="biobank.name" readonly="true"/></td>
                </tr>
                <tr>
                    <th><s:label name="biobank.address"/></th>
                    <td><s:text name="biobank.address" readonly="true"/></td>
                </tr>
            </table>
        </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>
