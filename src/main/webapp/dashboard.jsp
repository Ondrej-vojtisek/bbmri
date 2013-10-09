<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="signin" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.DashboardActionBean"/>
<s:layout-render name="/layouts/layout_login.jsp" title="${title}">
    <s:layout-component name="body">

        <fieldset>
        <s:form beanclass="bbmri.action.DashboardActionBean">
                        DASHBOARD

            <security:allowed bean="ab" event="iamsecure" >
                Something you want to do here.
            </security:allowed>

        </s:form>
        </fieldset>

    </s:layout-component>
</s:layout-render>