<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="user"
                 ternarymenu="roles">

<stripes:layout-component name="body">

    <table class="table table-hover table-striped">
        <thead>
        <tr>
            <th><format:message key="cz.bbmri.entities.enumeration.SystemRoles"/></th>
        </tr>
        </thead>
        <tbody>
        <core:if test="${empty actionBean.user.systemRoles}">
            <tr>
                <td><format:message key="empty"/></td>
            </tr>
        </core:if>
        <core:forEach items="${actionBean.user.systemRoles}" var="systemRole">
            <tr>
                <td>${systemRole}</td>
                <td class="action">
                    <span class="pull-right">
                    <security:allowed event="removeAdministratorRole">
                    <core:if test="${systemRole == 'ADMINISTRATOR'}">

                        <format:message var="questionAdministrator"
                                   key="cz.bbmri.action.user.UserActionBean.questionRemoveAdministratorRole"/>

                    <stripes:form beanclass="cz.bbmri.action.user.UserActionBean">
                        <stripes:submit name="removeAdministratorRole"
                                  class="btn btn-danger"
                                  onclick="return confirm('${questionAdministrator}')"/>
                        <stripes:param name="userId" value="${actionBean.userId}"/>
                    </stripes:form>
                    </core:if>
                    </security:allowed>

                    <security:allowed event="removeDeveloperRole">
                    <core:if test="${systemRole == 'DEVELOPER'}">
                        <format:message var="questionDeveloper"
                                   key="cz.bbmri.action.user.UserActionBean.questionRemoveDeveloperRole"/>

                    <stripes:form beanclass="cz.bbmri.action.user.UserActionBean">
                        <stripes:submit name="removeDeveloperRole"
                                  class="btn btn-danger"
                                  onclick="return confirm('${questionDeveloper}')"/>
                        <stripes:param name="userId" value="${actionBean.userId}"/>
                    </stripes:form>
                    </core:if>
                    </security:allowed>
                        </span>
                <td>
            </tr>
        </core:forEach>
        </tbody>
    </table>

    <core:if test="${not actionBean.isDeveloper}">
        <security:allowed event="setDeveloperRole">
            <fieldset>
                <legend><format:message key="cz.bbmri.action.user.UserActionBean.assign"/>
                    <format:message key="cz.bbmri.entities.enumeration.SystemRole.DEVELOPER"/>
                </legend>

                <stripes:form beanclass="cz.bbmri.action.user.UserActionBean">
                    <div class="form-actions">
                        <stripes:submit name="setDeveloperRole" class="btn btn-primary">
                            <stripes:param name="userId" value="${actionBean.userId}"/>
                        </stripes:submit>

                    </div>
                </stripes:form>

            </fieldset>
        </security:allowed>
    </core:if>

    <core:if test="${not actionBean.isAdministrator}">
        <security:allowed event="setAdministratorRole">
            <fieldset>
                <legend><format:message key="cz.bbmri.action.user.UserActionBean.assign"/>
                    <format:message key="cz.bbmri.entities.enumeration.SystemRole.ADMINISTRATOR"/>
                </legend>

                <stripes:form beanclass="cz.bbmri.action.user.UserActionBean">
                    <div class="form-actions">
                        <stripes:submit name="setAdministratorRole" class="btn btn-primary">
                            <stripes:param name="userId" value="${actionBean.userId}"/>
                        </stripes:submit>
                    </div>
                </stripes:form>
            </fieldset>
        </security:allowed>
    </core:if>


    <fieldset>
        <legend><format:message key="cz.bbmri.action.user.UserActionBean.myBiobanks"/></legend>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><format:message key="cz.bbmri.entities.webEntities.RoleDTO.subject"/></th>
                <th><format:message key="cz.bbmri.entities.webEntities.RoleDTO.permission"/></th>
                <th><format:message key="cz.bbmri.entities.webEntities.RoleDTO.referenceId"/></th>
            </tr>
            </thead>
            <tbody>

            <stripes:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             collection="${actionBean.user.biobankAdministrators}"/>

            <core:forEach items="${actionBean.user.biobankAdministrators}" var="ba">
                    <tr>
                        <td>${ba.biobank.abbreviation}</td>
                        <td>${ba.permission}</td>
                        <td class="action">
                            <span class="pull-right">

                                <stripes:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="detail"
                                        class="btn btn-info">
                                    <stripes:param name="biobankId" value="${roleDTO.referenceId}"/>
                                    <format:message key="detail"/>
                                </stripes:link>
                            </span>
                        </td>
                    </tr>
            </core:forEach>
            </tbody>
        </table>

    </fieldset>


    <fieldset>
        <legend><format:message key="cz.bbmri.action.user.UserActionBean.myProjects"/></legend>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><format:message key="cz.bbmri.entities.webEntities.RoleDTO.subject"/></th>
                <th><format:message key="cz.bbmri.entities.webEntities.RoleDTO.permission"/></th>
                <th><format:message key="cz.bbmri.entities.webEntities.RoleDTO.referenceId"/></th>
            </tr>
            </thead>
            <tbody>

            <stripes:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             collection="${actionBean.user.projectAdministrators}"/>

            <core:forEach items="${actionBean.user.projectAdministrators}" var="pa">
                    <tr>
                        <td>${pa.project.name}</td>
                        <td>${pa.permission}</td>

                        <td class="action">
                            <span class="pull-right">

                                <stripes:link beanclass="cz.bbmri.action.project.ProjectActionBean" event="detail"
                                        class="btn btn-info">
                                    <stripes:param name="projectId" value="${roleDTO.referenceId}"/>
                                    <format:message key="detail"/>
                                </stripes:link>
                            </span>
                        </td>
                    </tr>
            </core:forEach>
            </tbody>
        </table>

    </fieldset>


</stripes:layout-component>
</stripes:layout-render>