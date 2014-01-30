<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.user.UserActionBean.detail" var="title"/>

<s:useActionBean var="userBean" beanclass="cz.bbmri.action.user.UserActionBean"/>
<s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>
<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="user"
                 secondarymenu="user_all"
                 ternarymenu="roles">

<s:layout-component name="body">

<table class="table table-hover table-striped">
    <thead>
    <tr>
        <th><f:message key="cz.bbmri.entities.enumeration.SystemRoles"/></th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty userBean.systemRoles}">
        <tr>
            <td><f:message key="empty"/></td>
        </tr>
    </c:if>
    <c:forEach items="${userBean.systemRoles}" var="systemRole">
        <tr>
            <td>${systemRole}</td>
            <td class="action">
                <security:allowed bean="userBean" event="removeAdministratorRole">
                <c:if test="${systemRole == 'ADMINISTRATOR'}">

                    <f:message var="questionAdministrator"
                               key="cz.bbmri.action.user.UserActionBean.questionRemoveAdministratorRole"/>

                <s:form beanclass="cz.bbmri.action.user.UserActionBean">
                    <s:submit name="removeAdministratorRole"
                              class="btn btn-danger"
                              onclick="return confirm('${questionAdministrator}')"/>
                        <s:param name="userId" value="${userBean.userId}"/>
                </s:form>
                    <%--<s:link beanclass="cz.bbmri.action.user.UserActionBean"--%>
                    <%--event="removeAdministratorRole">--%>
                    <%--<f:message key="remove"/></s:link>--%>
                </c:if>
                </security:allowed>

                <security:allowed bean="userBean" event="removeDeveloperRole">
                <c:if test="${systemRole == 'DEVELOPER'}">
                    <f:message var="questionDeveloper"
                               key="cz.bbmri.action.user.UserActionBean.questionRemoveDeveloperRole"/>

                <s:form beanclass="cz.bbmri.action.user.UserActionBean">
                    <s:submit name="removeDeveloperRole"
                              class="btn btn-danger"
                              onclick="return confirm('${questionDeveloper}')"/>
                        <s:param name="userId" value="${userBean.userId}"/>
                </s:form>

                    <%--<s:link beanclass="cz.bbmri.action.user.UserActionBean"--%>
                    <%--event="removeDeveloperRole">--%>
                    <%--<f:message key="remove"/></s:link>--%>
                </c:if>
                </security:allowed>
            <td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${not userBean.isDeveloper}">
    <security:allowed bean="userBean" event="setDeveloperRole">
        <fieldset>
            <legend><f:message key="cz.bbmri.action.user.UserActionBean.assign"/>
                <f:message key="cz.bbmri.entities.enumeration.SystemRole.DEVELOPER"/>
            </legend>

            <s:form beanclass="cz.bbmri.action.user.UserActionBean">
                <div class="form-actions">
                    <s:submit name="setDeveloperRole" class="btn btn-primary">
                        <s:param name="userId" value="${userBean.userId}"/>
                    </s:submit>

                </div>
            </s:form>

                <%--<s:link beanclass="cz.bbmri.action.user.UserActionBean"--%>
                <%--event="setDeveloperRole">--%>
                <%--<f:message key="set"/></s:link>--%>

        </fieldset>
    </security:allowed>
</c:if>

<c:if test="${not userBean.isAdministrator}">
    <security:allowed bean="userBean" event="setAdministratorRole">
        <fieldset>
            <legend><f:message key="cz.bbmri.action.user.UserActionBean.assign"/>
                <f:message key="cz.bbmri.entities.enumeration.SystemRole.ADMINISTRATOR"/>
            </legend>

            <s:form beanclass="cz.bbmri.action.user.UserActionBean">
                <div class="form-actions">
                    <s:submit name="setAdministratorRole" class="btn btn-primary">
                        <s:param name="userId" value="${userBean.userId}"/>
                    </s:submit>
                </div>
            </s:form>

                <%--<s:link beanclass="cz.bbmri.action.user.UserActionBean"--%>
                <%--event="setAdministratorRole">--%>
                <%--<f:message key="set"/></s:link>--%>
        </fieldset>
    </security:allowed>
</c:if>


<fieldset>
    <legend><f:message key="cz.bbmri.action.user.UserActionBean.myBiobanks"/></legend>
    <table class="table table-striped">
        <thead>
        <tr>
            <th><f:message key="cz.bbmri.entities.webEntities.RoleDTO.subject"/></th>
            <th><f:message key="cz.bbmri.entities.webEntities.RoleDTO.permission"/></th>
            <th><f:message key="cz.bbmri.entities.webEntities.RoleDTO.referenceId"/></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty userBean.userRoles}">
            <tr>
                <td colspan="3"><f:message key="empty"/></td>
            </tr>
        </c:if>
        <c:forEach items="${userBean.userRoles}" var="roleDTO">
            <c:if test="${roleDTO.type.name == 'cz.bbmri.entities.BiobankAdministrator'}">
                <tr>
                    <td>${roleDTO.subject}</td>
                    <td>${roleDTO.permission}</td>

                        <%--Necessary to distinguish between BiobankAdministrator and ProjectAdministrator--%>
                    <td class="action">

                            <%--Need to set id for each row of a table to set properly ACL--%>

                        <c:set target="${biobankBean}" property="biobankId" value="${roleDTO.referenceId}"/>

                        <security:allowed bean="biobankBean" event="edit">
                            <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="edit"
                                    class="btn btn-primary">
                                <s:param name="biobankId" value="${roleDTO.referenceId}"/>
                                <f:message key="edit"/>
                            </s:link>
                        </security:allowed>
                        <security:notAllowed bean="biobankBean" event="edit">
                            <security:allowed bean="biobankBean" event="detail">
                                <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="detail"
                                        class="btn btn-infor">
                                    <s:param name="biobankId" value="${roleDTO.referenceId}"/>
                                    <f:message key="detail"/>
                                </s:link>
                            </security:allowed>
                        </security:notAllowed>


                    </td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>

</fieldset>


<fieldset>
    <legend><f:message key="cz.bbmri.action.user.UserActionBean.myProjects"/></legend>
    <table class="table table-striped">
        <thead>
        <tr>
            <th><f:message key="cz.bbmri.entities.webEntities.RoleDTO.subject"/></th>
            <th><f:message key="cz.bbmri.entities.webEntities.RoleDTO.permission"/></th>
            <th><f:message key="cz.bbmri.entities.webEntities.RoleDTO.referenceId"/></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty userBean.userRoles}">
            <tr>
                <td colspan="3"><f:message key="empty"/></td>
            </tr>
        </c:if>
        <c:forEach items="${userBean.userRoles}" var="roleDTO">
            <c:if test="${roleDTO.type.name == 'cz.bbmri.entities.ProjectAdministrator'}">
                <tr>
                    <td>${roleDTO.subject}</td>
                    <td>${roleDTO.permission}</td>

                    <td class="action">

                            <%--Necessary to distinguish between BiobankAdministrator and ProjectAdministrator--%>


                            <%--Need to set id for each row of a table to set properly ACL--%>

                        <c:set target="${projectBean}" property="projectId" value="${roleDTO.referenceId}"/>

                        <security:allowed bean="projectBean" event="edit">
                            <s:link beanclass="cz.bbmri.action.project.ProjectActionBean" event="edit"
                                    class="btn btn-primary">
                                <s:param name="projectId" value="${roleDTO.referenceId}"/>
                                <f:message key="edit"/>
                            </s:link>
                        </security:allowed>
                        <security:notAllowed bean="projectBean" event="edit">
                            <security:allowed bean="projectBean" event="detail">
                                <s:link beanclass="cz.bbmri.action.project.ProjectActionBean" event="detail"
                                        class="btn btn-info">
                                    <s:param name="projectId" value="${roleDTO.referenceId}"/>
                                    <f:message key="detail"/>
                                </s:link>
                            </security:allowed>
                        </security:notAllowed>

                    </td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>

</fieldset>


</s:layout-component>
</s:layout-render>