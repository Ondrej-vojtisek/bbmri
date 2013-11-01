<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="biobank.detail" var="title"/>

<s:useActionBean var="biobankBean" beanclass="bbmri.action.biobank.BiobankActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="administrators">

    <s:layout-component name="body">

        WRITE

        <s:layout-render name="/webpages/biobank/component/administrators.jsp" editable="true"/>

        <%--<fieldset>--%>
            <%--<s:layout-render name="/webpages/component/findUser.jsp" bean="${biobankBean}" eventName="selectAdministrator"/>--%>
        <%--</fieldset>--%>

    </s:layout-component>
</s:layout-render>
