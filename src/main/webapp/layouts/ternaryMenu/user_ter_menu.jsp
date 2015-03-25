<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="userBean" beanclass="cz.bbmri.action.UserActionBean"/>
<s:useActionBean var="roleBean" beanclass="cz.bbmri.action.RoleActionBean"/>
<s:useActionBean var="settingsBean" beanclass="cz.bbmri.action.SettingsActionBean"/>


<s:layout-definition>

    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="userBean" event="detail">
        <li <c:if test="${ternarymenu == 'personal_data'}"> class="active" </c:if> >
            <s:link beanclass="cz.bbmri.action.UserActionBean" event="detail">
                <s:param name="id" value="${actionBean.id}"/>
                <f:message key="cz.bbmri.action.UserActionBean.basicData"/>
            </s:link></li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="userBean" event="changePasswordResolution">
        <li <c:if test="${ternarymenu == 'password'}"> class="active" </c:if>>
            <s:link beanclass="cz.bbmri.action.UserActionBean"
                    event="changePasswordResolution">
                <s:param name="id" value="${actionBean.id}"/>
                <f:message key="cz.bbmri.entities.User.password"/>
            </s:link>
        </li>
    </security:allowed>


    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="userBean" event="shibbolethResolution">
        <li <c:if test="${ternarymenu == 'shibboleth'}"> class="active" </c:if>>
            <s:link beanclass="cz.bbmri.action.UserActionBean"
                    event="shibbolethResolution">
                <s:param name="id" value="${userBean.id}"/>
                <f:message key="cz.bbmri.entities.User.shibboleth"/>
            </s:link>
        </li>
    </security:allowed>

    <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>

    <security:allowed bean="roleBean" event="detail">
        <li <c:if test="${ternarymenu == 'roles'}"> class="active" </c:if>>
            <s:link beanclass="cz.bbmri.action.RoleActionBean"
                    event="detail">
                <s:param name="id" value="${actionBean.id}"/>
                <f:message key="cz.bbmri.entity.Role.roles"/>
            </s:link>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>

    <%--<security:allowed bean="settingsBean" event="detail">--%>
        <%--<li <c:if test="${ternarymenu == 'settings'}"> class="active" </c:if>>--%>
            <%--<s:link beanclass="cz.bbmri.action.SettingsActionBean"--%>
                    <%--event="detail">--%>
                <%--<s:param name="id" value="${actionBean.id}"/>--%>
                <%--<f:message key="cz.bbmri.action.SettingsActionBean.settings"/>--%>
            <%--</s:link>--%>
        <%--</li>--%>
    <%--</security:allowed>--%>

    <%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>

</s:layout-definition>

