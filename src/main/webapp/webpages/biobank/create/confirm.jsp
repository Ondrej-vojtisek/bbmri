<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.biobank.BiobankActionBean.create" var="title"/>
<s:useActionBean var="biobankCreateBean" beanclass="bbmri.action.biobank.CreateActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 secondarymenu="biobank_create">

    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.biobank.CreateActionBean" method="GET">
                  <s:submit name="administratorsBack" class="btn btn-inverse"/>
        </s:form>

        <s:form beanclass="bbmri.action.biobank.CreateActionBean">

            <fieldset>

                <s:hidden name="project.id"/>
                <legend><f:message key="bbmri.action.biobank.CreateActionBean.thirdStep"/></legend>

                <table>
                    <tr>
                        <th><s:label name="biobank.name"/></th>
                        <td><s:text name="newBiobank.name" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><s:label name="biobank.address"/></th>
                        <td><s:text name="newBiobank.address" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><s:label name="user"/></th>
                        <td><s:text name="newAdministrator.wholeName" readonly="true"/></td>
                    </tr>
                </table>

                <s:submit name="done" class="btn btn-primary"/>

            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>