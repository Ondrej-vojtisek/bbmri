<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="userBean" beanclass="cz.bbmri.action.user.UserActionBean"/>
<s:useActionBean var="userFindBean" beanclass="cz.bbmri.action.user.FindUserActionBean"/>

<%--<li <c:if test="${secondarymenu == 'user_all'}"> class="active" </c:if> ><s:link--%>
        <%--beanclass="cz.bbmri.action.user.UserActionBean"><f:message--%>
        <%--key="all"/></s:link></li>--%>

<security:allowed bean="userBean" event="allUsers">
    <li <c:if test="${secondarymenu == 'user_all'}"> class="active" </c:if>><s:link
            beanclass="${userBean.name}"><f:message
            key="all"/></s:link></li>
</security:allowed>

<security:allowed bean="userBean" event="createUser">
    <li <c:if test="${secondarymenu == 'user_create'}"> class="active" </c:if>><s:link
            beanclass="${userBean.name}" event="createUser"><f:message
            key="user.create"/></s:link></li>
</security:allowed>

<security:allowed bean="userFindBean" event="find">
    <li <c:if test="${secondarymenu == 'user_find'}"> class="active" </c:if>><s:link
            beanclass="${userFindBean.name}" event="find"><f:message
            key="user.find"/></s:link></li>
</security:allowed>