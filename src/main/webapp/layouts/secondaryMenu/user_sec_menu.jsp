<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="userBean" beanclass="bbmri.action.user.UserActionBean"/>

<%--<li <c:if test="${secondarymenu == 'user_all'}"> class="active" </c:if> ><s:link--%>
        <%--beanclass="bbmri.action.user.UserActionBean"><f:message--%>
        <%--key="all"/></s:link></li>--%>

<security:allowed bean="userBean" event="allUsers">
    <li <c:if test="${secondarymenu == 'user_all'}"> class="active" </c:if>><s:link
            beanclass="bbmri.action.user.UserActionBean"><f:message
            key="all"/></s:link></li>
</security:allowed>

<security:allowed bean="userBean" event="createUser">
    <li <c:if test="${secondarymenu == 'user_create'}"> class="active" </c:if>><s:link
            beanclass="bbmri.action.user.UserActionBean" event="createUser"><f:message
            key="user.create"/></s:link></li>
</security:allowed>

<security:allowed bean="userBean" event="findResolution">
    <li <c:if test="${secondarymenu == 'user_find'}"> class="active" </c:if>><s:link
            beanclass="bbmri.action.user.UserActionBean" event="findResolution"><f:message
            key="user.find"/></s:link></li>
</security:allowed>