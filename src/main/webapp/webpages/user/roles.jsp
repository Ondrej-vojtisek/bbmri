<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="user"
                 ternarymenu="roles">

<s:layout-component name="body">

    <table class="table table-hover table-striped">
        <thead>
        <tr>
            <th><f:message key="cz.bbmri.entities.enumeration.SystemRoles"/></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty actionBean.user.systemRoles}">
            <tr>
                <td><f:message key="empty"/></td>
            </tr>
        </c:if>
        <c:forEach items="${actionBean.user.systemRoles}" var="systemRole">
            <tr>
                <td>${systemRole}</td>
                <td class="action">
                    <span class="pull-right">
                    <security:allowed event="removeAdministratorRole">
                    <c:if test="${systemRole == 'ADMINISTRATOR'}">

                        <f:message var="questionAdministrator"
                                   key="cz.bbmri.action.user.UserActionBean.questionRemoveAdministratorRole"/>

                    <s:form beanclass="cz.bbmri.action.user.UserActionBean">
                        <s:submit name="removeAdministratorRole"
                                  class="btn btn-danger"
                                  onclick="return confirm('${questionAdministrator}')"/>
                        <s:param name="userId" value="${actionBean.userId}"/>
                    </s:form>
                    </c:if>
                    </security:allowed>

                    <security:allowed event="removeDeveloperRole">
                    <c:if test="${systemRole == 'DEVELOPER'}">
                        <f:message var="questionDeveloper"
                                   key="cz.bbmri.action.user.UserActionBean.questionRemoveDeveloperRole"/>

                    <s:form beanclass="cz.bbmri.action.user.UserActionBean">
                        <s:submit name="removeDeveloperRole"
                                  class="btn btn-danger"
                                  onclick="return confirm('${questionDeveloper}')"/>
                        <s:param name="userId" value="${actionBean.userId}"/>
                    </s:form>
                    </c:if>
                    </security:allowed>
                        </span>
                <td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${not actionBean.isDeveloper}">
        <security:allowed event="setDeveloperRole">
            <fieldset>
                <legend><f:message key="cz.bbmri.action.user.UserActionBean.assign"/>
                    <f:message key="cz.bbmri.entities.enumeration.SystemRole.DEVELOPER"/>
                </legend>

                <s:form beanclass="cz.bbmri.action.user.UserActionBean">
                    <div class="form-actions">
                        <s:submit name="setDeveloperRole" class="btn btn-primary">
                            <s:param name="userId" value="${actionBean.userId}"/>
                        </s:submit>

                    </div>
                </s:form>

            </fieldset>
        </security:allowed>
    </c:if>

    <c:if test="${not actionBean.isAdministrator}">
        <security:allowed event="setAdministratorRole">
            <fieldset>
                <legend><f:message key="cz.bbmri.action.user.UserActionBean.assign"/>
                    <f:message key="cz.bbmri.entities.enumeration.SystemRole.ADMINISTRATOR"/>
                </legend>

                <s:form beanclass="cz.bbmri.action.user.UserActionBean">
                    <div class="form-actions">
                        <s:submit name="setAdministratorRole" class="btn btn-primary">
                            <s:param name="userId" value="${actionBean.userId}"/>
                        </s:submit>
                    </div>
                </s:form>
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

            <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             collection="${actionBean.user.biobankAdministrators}"/>

            <c:forEach items="${actionBean.user.biobankAdministrators}" var="ba">
                    <tr>
                        <td>${ba.biobank.abbreviation}</td>
                        <td>${ba.permission}</td>
                        <td class="action">
                            <span class="pull-right">

                                <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="detail"
                                        class="btn btn-info">
                                    <s:param name="biobankId" value="${roleDTO.referenceId}"/>
                                    <f:message key="detail"/>
                                </s:link>
                            </span>
                        </td>
                    </tr>
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

            <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             collection="${actionBean.user.projectAdministrators}"/>

            <c:forEach items="${actionBean.user.projectAdministrators}" var="pa">
                    <tr>
                        <td>${pa.project.name}</td>
                        <td>${pa.permission}</td>

                        <td class="action">
                            <span class="pull-right">

                                <s:link beanclass="cz.bbmri.action.project.ProjectActionBean" event="detail"
                                        class="btn btn-info">
                                    <s:param name="projectId" value="${roleDTO.referenceId}"/>
                                    <f:message key="detail"/>
                                </s:link>
                            </span>
                        </td>
                    </tr>
            </c:forEach>
            </tbody>
        </table>

    </fieldset>


</s:layout-component>
</s:layout-render>