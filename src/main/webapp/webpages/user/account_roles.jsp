<%@ page import="bbmri.entities.enumeration.RoleType" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="credentials.myRoles" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.user.AccountActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="account"
                 ternarymenu="roles">

    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="RoleType"/></legend>
            <table cellspacing="0" class="tablesorter">
                <thead>
                <tr>
                    <th><s:label name="roleType"/></th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty ab.roleTypes}">
                    <tr>
                        <td><f:message key="empty"/></td>
                    </tr>
                </c:if>
                <c:forEach items="${ab.roleTypes}" var="roleType">
                    <tr>
                        <td><c:out value="${roleType}"/></td>

                        <c:if test="${roleType == 'ADMINISTRATOR'}">
                            <td>
                                <s:link beanclass="bbmri.action.user.AccountActionBean"
                                        event="refuseAdministratorRole">
                                    <f:message key="remove"/></s:link>
                            </td>
                        </c:if>

                        <c:if test="${roleType == 'DEVELOPER'}">
                            <td>
                                <s:link beanclass="bbmri.action.user.AccountActionBean"
                                        event="refuseDeveloperRole">
                                <f:message key="remove"/></s:link>
                            </td>
                        </c:if>


                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </fieldset>


               <%
                   if (!ab.getRoleTypes().contains(RoleType.DEVELOPER)) {
               %>
               <fieldset>
                   <legend><f:message key="RoleType"/></legend>

                       <td>
                           <s:link beanclass="bbmri.action.user.AccountActionBean"
                                   event="setDeveloperRole">
                               <f:message key="set"/></s:link>
                       </td>
               </fieldset>
               <%
                   }
               %>

               <%
                   if (!ab.getRoleTypes().contains(RoleType.ADMINISTRATOR)) {
               %>
               <fieldset>
                   <legend><f:message key="RoleType"/></legend>
                       <td>
                           <s:link beanclass="bbmri.action.user.AccountActionBean"
                                   event="setAdministratorRole">
                               <f:message key="set"/></s:link>
                       </td>
               </fieldset>
               <%
                   }
               %>


        <fieldset>
            <legend><f:message key="credentials.roles"/></legend>
            <table cellspacing="0" class="tablesorter">
                <thead>
                <tr>
                    <th><s:label name="role"/></th>
                    <th><s:label name="permission"/></th>
                    <th><s:label name="reference"/></th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty ab.userRoles}">
                    <tr>
                        <td><f:message key="empty"/></td>
                    </tr>
                </c:if>
                <c:forEach items="${ab.userRoles}" var="roleDTO">
                    <tr>
                        <td><c:out value="${roleDTO.subject}"/></td>
                        <td><c:out value="${roleDTO.permission}"/></td>
                        <td><c:out value="${roleDTO.referenceId}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </fieldset>

    </s:layout-component>
</s:layout-render>