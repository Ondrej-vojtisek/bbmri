<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.biobank.BiobankActionBean.create" var="title"/>

<s:useActionBean var="biobankCreateBean" beanclass="bbmri.action.biobank.CreateActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 secondarymenu="biobank_create">

    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.biobank.CreateActionBean" method="GET">
            <s:submit name="generalBack" class="btn btn-inverse"/>
        </s:form>

        <fieldset>
            <legend><f:message key="bbmri.action.biobank.CreateActionBean.secondStep"/></legend>
            <s:layout-render name="/webpages/component/findUser.jsp"
                             context="biobankCreate"
                             findBean="${biobankCreateBean}"/>
        </fieldset>
    </s:layout-component>
</s:layout-render>