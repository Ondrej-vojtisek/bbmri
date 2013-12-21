<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.support.SupportActionBean.title" var="title"/>

<s:useActionBean var="actionBean" beanclass="cz.bbmri.action.support.SupportActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp"
                 title="${title}" primarymenu="support">

    <s:layout-component name="body">
        <fieldset>
            <legend><f:message key="cz.bbmri.action.support.SupportActionBean.administrators"/></legend>
            <%--<f:message key="cz.bbmri.action.support.SupportActionBean.needAdministratorAssistance">--%>
        </fieldset>

        <fieldset>
            <legend><f:message key="cz.bbmri.action.support.SupportActionBean.developers"/></legend>
            <%--<f:message key="cz.bbmri.action.support.SupportActionBean.needAdministratorAssistance">--%>
        </fieldset>

    </s:layout-component>

</s:layout-render>

