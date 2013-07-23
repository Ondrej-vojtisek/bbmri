<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="signin" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.DashboardActionBean"/>
<s:layout-render name="/layout_login.jsp" title="${title}">
    <s:layout-component name="body">

        <fieldset>
        <s:form beanclass="bbmri.action.LoginActionBean">

                <s:submit name="logout"><f:message key="logout"/></s:submit>
        </s:form>
        <s:form beanclass="bbmri.action.DashboardActionBean">
                        DASHBOARD
                 <s:text name="data"/>


            <security:allowed bean="ab" event="iamsecure">
                Something you want to do here.
            </security:allowed>

            <security:allowed bean="ab" event="develop">
                            Something you want to do here.
                        </security:allowed>


        </s:form>
        </fieldset>

    </s:layout-component>
</s:layout-render>