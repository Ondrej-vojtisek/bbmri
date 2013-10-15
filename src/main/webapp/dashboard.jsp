<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="signin" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.DashboardActionBean"/>
<s:layout-render name="/layouts/layout_login.jsp" title="${title}">
    <s:layout-component name="body">

        <fieldset>
            <s:form beanclass="bbmri.action.DashboardActionBean">
                DASHBOARD

                <security:allowed bean="ab" event="iamsecure">
                    USER
                </security:allowed>
                ////
                <security:allowed bean="ab" event="iamsecure2">
                    Administrator
                </security:allowed>
                ////
                <security:allowed bean="ab" event="iamsecure3">
                    1 == 1
                </security:allowed>
                ////
                <security:allowed bean="ab" event="iamsecure4">
                    1 == 2
                </security:allowed>
                ////
                <security:allowed bean="ab" event="iamsecure5">
                    cisloA == cisloB tj. 1==2
                </security:allowed>
                ////
                <security:allowed bean="ab" event="iamsecure6">
                    getCisloA == getCisloB
                </security:allowed>
                ////
                <security:allowed bean="ab" event="iamsecure7">
                    cisloAA == cisloBB
                </security:allowed>
                ////
                <security:allowed bean="ab" event="iamsecure8">
                    myId == 2
                </security:allowed>
                ////
                <security:allowed bean="ab" event="iamsecure10">
                    pravda
                </security:allowed>

            </s:form>
        </fieldset>

    </s:layout-component>
</s:layout-render>