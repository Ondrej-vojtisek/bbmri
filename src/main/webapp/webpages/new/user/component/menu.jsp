<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="userBean" beanclass="cz.bbmri.action.UserActionBean"/>
<stripes:useActionBean var="roleBean" beanclass="cz.bbmri.action.RoleActionBean"/>
<stripes:useActionBean var="settingsBean" beanclass="cz.bbmri.action.SettingsActionBean"/>


<stripes:layout-definition>

    <ul class="nav nav-tabs">

    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="userBean" event="detail">
        <li <core:if test="${active == 'personal_data'}"> class="active" </core:if> >
            <stripes:link beanclass="cz.bbmri.action.UserActionBean" event="detail">
                <stripes:param name="id" value="${actionBean.id}"/>
                <format:message key="cz.bbmri.action.UserActionBean.basicData"/>
            </stripes:link></li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="userBean" event="changePasswordResolution">
        <li <core:if test="${active == 'password'}"> class="active" </core:if>>
            <stripes:link beanclass="cz.bbmri.action.UserActionBean"
                    event="changePasswordResolution">
                <stripes:param name="id" value="${actionBean.id}"/>
                <format:message key="cz.bbmri.entities.User.password"/>
            </stripes:link>
        </li>
    </security:allowed>


    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="userBean" event="shibbolethResolution">
        <li <core:if test="${active == 'shibboleth'}"> class="active" </core:if>>
            <stripes:link beanclass="cz.bbmri.action.UserActionBean"
                    event="shibbolethResolution">
                <stripes:param name="id" value="${userBean.id}"/>
                <format:message key="cz.bbmri.entities.User.shibboleth"/>
            </stripes:link>
        </li>
    </security:allowed>

    <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>

    <security:allowed bean="roleBean" event="detail">
        <li <core:if test="${active == 'roles'}"> class="active" </core:if>>
            <stripes:link beanclass="cz.bbmri.action.RoleActionBean"
                    event="detail">
                <stripes:param name="id" value="${actionBean.id}"/>
                <format:message key="cz.bbmri.entity.Role.roles"/>
            </stripes:link>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>

    <%--<security:allowed bean="settingsBean" event="detail">--%>
        <%--<li <core:if test="${active == 'settings'}"> class="active" </core:if>>--%>
            <%--<stripes:link beanclass="cz.bbmri.action.SettingsActionBean"--%>
                    <%--event="detail">--%>
                <%--<stripes:param name="id" value="${actionBean.id}"/>--%>
                <%--<format:message key="cz.bbmri.action.SettingsActionBean.settings"/>--%>
            <%--</stripes:link>--%>
        <%--</li>--%>
    <%--</security:allowed>--%>

    <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>

    </ul>

</stripes:layout-definition>

