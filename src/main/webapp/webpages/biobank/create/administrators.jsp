<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="biobank.create" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.biobank.CreateActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 secondarymenu="biobank_create">

    <s:layout-component name="body">
            <fieldset>
                <s:layout-render name="/webpages/component/findUser.jsp" bean="${ab}" eventName="selectAdministrator"/>
            </fieldset>
    </s:layout-component>
</s:layout-render>