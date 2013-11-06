<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="credentials.myRoles" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.user.UserActionBean"/>
<s:useActionBean var="biobankBean" beanclass="bbmri.action.biobank.BiobankActionBean"/>
<s:useActionBean var="projectBean" beanclass="bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="user"
                 secondarymenu="user_all"
                 ternarymenu="roles">

    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="bbmri.entities.enumeration.SystemRoles"/></legend>
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th><s:label name="bbmri.entities.enumeration.SystemRoles"/></th>
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
                        <td><c:out value="${systemRole}"/></td>
                        <security:allowed bean="ab" event="removeAdministratorRole">
                            <td>
                                <c:if test="${systemRole == 'ADMINISTRATOR'}">
                                    <s:link beanclass="bbmri.action.user.UserActionBean"
                                            event="removeAdministratorRole">
                                        <f:message key="remove"/></s:link>
                                </c:if>
                            </td>
                        </security:allowed>

                        <security:allowed bean="ab" event="removeDeveloperRole">
                            <td>
                                <c:if test="${systemRole == 'DEVELOPER'}">
                                    <s:link beanclass="bbmri.action.user.UserActionBean"
                                            event="removeDeveloperRole">
                                        <f:message key="remove"/></s:link>
                                </c:if>
                            </td>
                        </security:allowed>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </fieldset>


        <c:if test="${not ab.isDeveloper}">
            <security:allowed bean="ab" event="setDeveloperRole">
                <fieldset>
                    <legend><f:message key="bbmri.action.user.UserActionBean.assign"/>
                        <f:message key="bbmri.entities.enumeration.SystemRole.DEVELOPER"/>
                    </legend>

                    <s:link beanclass="bbmri.action.user.UserActionBean"
                            event="setDeveloperRole">
                        <f:message key="set"/></s:link>

                </fieldset>
            </security:allowed>
        </c:if>

        <c:if test="${not ab.isAdministrator}">
            <security:allowed bean="ab" event="setAdministratorRole">
                <fieldset>
                    <legend><f:message key="bbmri.action.user.UserActionBean.assign"/>
                        <f:message key="bbmri.entities.enumeration.SystemRole.ADMINISTRATOR"/>
                    </legend>

                    <s:link beanclass="bbmri.action.user.UserActionBean"
                            event="setAdministratorRole">
                        <f:message key="set"/></s:link>
                </fieldset>
            </security:allowed>
        </c:if>


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

                            <c:if test="${roleDTO.type.name == 'bbmri.entities.ProjectAdministrator'}">

                                <%--Need to set id for each row of a table to set properly ACL--%>
                                <c:set target="${projectBean}" property="id" value="${roleDTO.referenceId}"/>

                                <security:allowed bean="projectBean" event="edit">
                                    <s:link beanclass="bbmri.action.project.ProjectActionBean" event="edit">
                                        <s:param name="id" value="${roleDTO.referenceId}"/><f:message
                                            key="edit"/></s:link>
                                </security:allowed>
                                <security:notAllowed bean="projectBean" event="edit">
                                    <security:allowed bean="projectBean" event="detail">
                                        <s:link beanclass="bbmri.action.project.ProjectActionBean" event="detail">
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