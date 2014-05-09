<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="userBean" beanclass="cz.bbmri.action.user.UserActionBean"/>


<s:layout-definition>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="userBean" event="detail">
    <li <c:if test="${ternarymenu == 'personal_data'}"> class="active" </c:if> >
        <s:link beanclass="cz.bbmri.action.user.UserActionBean" event="detail">
            <s:param name="userId" value="${userBean.userId}"/>
            <f:message key="cz.bbmri.action.user.UserActionBean.basicData"/>
        </s:link></li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="userBean" event="changePasswordView">
    <li <c:if test="${ternarymenu == 'password'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.user.UserActionBean"
                event="changePasswordView">
            <s:param name="userId" value="${userBean.userId}"/>
            <f:message key="cz.bbmri.entities.User.password"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="userBean" event="rolesView">
    <li <c:if test="${ternarymenu == 'roles'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.user.UserActionBean"
                event="rolesView">
            <s:param name="userId" value="${userBean.userId}"/>
            <f:message key="cz.bbmri.entities.enumeration.SystemRoles"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<%--<security:allowed bean="userBean" event="mySettingResolution">--%>
    <%--<li <c:if test="${ternarymenu == 'setting'}"> class="active" </c:if>>--%>
        <%--<s:link beanclass="cz.bbmri.action.user.UserActionBean"--%>
                <%--event="mySettingResolution">--%>
            <%--<s:param name="userId" value="${userBean.userId}"/>--%>
            <%--<f:message key="cz.bbmri.action.user.UserActionBean.setting"/>--%>
        <%--</s:link>--%>
    <%--</li>--%>
<%--</security:allowed>--%>

<%--&lt;%&ndash; -------------------------------------------------------------------- &ndash;%&gt;--%>

</s:layout-definition>

