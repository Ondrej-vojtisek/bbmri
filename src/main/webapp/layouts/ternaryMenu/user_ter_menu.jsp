<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="userBean" beanclass="cz.bbmri.action.user.UserActionBean"/>

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
            <f:message key="cz.bbmri.action.user.UserActionBean.password"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="userBean" event="rolesView">
    <li <c:if test="${ternarymenu == 'roles'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.user.UserActionBean"
                event="rolesView">
            <s:param name="userId" value="${userBean.userId}"/>
            <f:message key="cz.bbmri.action.user.UserActionBean.roles"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>
