<%@ page import="bbmri.entities.enumeration.SystemRole" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="credentials.myRoles" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.user.AccountActionBean"/>
<s:useActionBean var="biobankBean" beanclass="bbmri.action.biobank.BiobankActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="account"
                 ternarymenu="roles">

    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="bbmri.entities.enumeration.SystemRoles"/></legend>
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th><s:label name="bbmri.entities.enumeration.SystemRole"/></th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty ab.systemRoles}">
                    <tr>
                        <td><f:message key="empty"/></td>
                    </tr>
                </c:if>
                <c:forEach items="${ab.systemRoles}" var="systemRole">
                    <tr>
                        <td>${systemRole}</td>
                        <td>
                            <c:if test="${systemRole == 'ADMINISTRATOR'}">

                                <s:link beanclass="bbmri.action.user.AccountActionBean"
                                        event="refuseAdministratorRole">
                                    <f:message key="remove"/></s:link>

                            </c:if>
                            <c:if test="${systemRole == 'DEVELOPER'}">

                                <s:link beanclass="bbmri.action.user.AccountActionBean"
                                        event="refuseDeveloperRole">
                                    <f:message key="remove"/></s:link>

                            </c:if>
                        </td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </fieldset>


        <%
            if (!ab.getSystemRoles().contains(SystemRole.DEVELOPER)) {
        %>
        <fieldset>
            <legend><f:message key="bbmri.action.user.UserActionBean.assign"/>
                <f:message key="bbmri.entities.enumeration.SystemRole.DEVELOPER"/>
            </legend>
            <s:link beanclass="bbmri.action.user.AccountActionBean"
                    event="setDeveloperRole">
                <f:message key="set"/></s:link>
        </fieldset>
        <%
            }
        %>

        <%
            if (!ab.getSystemRoles().contains(SystemRole.ADMINISTRATOR)) {
        %>
        <fieldset>
            <legend><f:message key="bbmri.action.user.UserActionBean.assign"/>
                <f:message key="bbmri.entities.enumeration.SystemRole.ADMINISTRATOR"/>
            </legend>
            <s:link beanclass="bbmri.action.user.AccountActionBean"
                    event="setAdministratorRole">
                <f:message key="set"/></s:link>
        </fieldset>
        <%
            }
        %>


        <fieldset>
            <legend><f:message key="credentials.roles"/></legend>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><s:label name="bbmri.entities.webEntities.RoleDTO.subject"/></th>
                    <th><s:label name="bbmri.entities.webEntities.RoleDTO.permission"/></th>
                    <th><s:label name="bbmri.entities.webEntities.RoleDTO.referenceId"/></th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty ab.userRoles}">
                    <tr>
                        <td colspan="3"><f:message key="empty"/></td>
                    </tr>
                </c:if>
                <c:forEach items="${ab.userRoles}" var="roleDTO">
                    <tr>
                        <td><c:out value="${roleDTO.subject}"/></td>
                        <td><c:out value="${roleDTO.permission}"/></td>
                        <td>

                            <%--Necessary to distinguish between BiobankAdministrator and ProjectAdministrator--%>
                            <c:if test="${roleDTO.type.name == 'bbmri.entities.BiobankAdministrator'}">

                                <%--Need to set id for each row of a table to set properly ACL--%>
                                <c:set target="${biobankBean}" property="id" value="${roleDTO.referenceId}"/>

                                <security:allowed bean="biobankBean" event="edit">
                                    <s:link beanclass="bbmri.action.biobank.BiobankActionBean" event="edit">
                                        <s:param name="id" value="${roleDTO.referenceId}"/><f:message
                                            key="edit"/></s:link>
                                </security:allowed>
                                <security:notAllowed bean="biobankBean" event="edit">
                                    <security:allowed bean="biobankBean" event="detail">
                                        <s:link beanclass="bbmri.action.biobank.BiobankActionBean" event="detail">
                                            <s:param name="id" value="${roleDTO.referenceId}"/><f:message
                                                key="detail"/></s:link>
                                    </security:allowed>
                                </security:notAllowed>
                            </c:if>
                            <%--TODO: Project edit vs. detail--%>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </fieldset>

    </s:layout-component>
</s:layout-render>