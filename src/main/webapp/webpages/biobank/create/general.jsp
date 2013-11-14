<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="bbmri.action.biobank.BiobankActionBean.create" var="title"/>
<s:useActionBean var="biobankCreateBean" beanclass="bbmri.action.biobank.CreateActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 secondarymenu="biobank_create">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.biobank.CreateActionBean">
            <fieldset>

                <legend><f:message key="bbmri.action.biobank.CreateActionBean.firstStep"/></legend>

                <table>
                    <tr>
                        <th><s:label for="z1" name="biobank.name"/></th>
                        <td><s:text id="z1" name="newBiobank.name"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z2" name="biobank.address"/></th>
                        <td><s:text id="z2" name="newBiobank.address"/></td>
                    </tr>
                </table>

                <s:submit name="administrators" class="btn btn-primary"/>

            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>