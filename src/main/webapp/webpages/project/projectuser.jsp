<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean beanclass="cz.bbmri.action.PermissionActionBean" var="permissionActionBean"/>
<stripes:useActionBean beanclass="cz.bbmri.action.ProjectUserActionBean" var="projectUserActionBean"/>

<stripes:layout-render name="${component.layout.content}"
                       primarymenu="project">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.project}" active="projectuser"/>

        <%--Set authProjectId of AuthotizationActionBean to enable security tag--%>
        <core:set target="${projectUserActionBean}" property="authProjectId" value="${actionBean.id}"/>

        <security:allowed bean="projectUserActionBean" event="add">
            <div class="form-actions">
                <stripes:link beanclass="cz.bbmri.action.ProjectUserActionBean"
                              name="add" class="btn btn-primary btnMargin">
                    <format:message key="cz.bbmri.entity.ProjectUser.add"/>
                    <stripes:param name="projectId" value="${actionBean.project.id}"/>
                </stripes:link>
            </div>
        </security:allowed>

        <table class="table table-hover table-striped">

            <stripes:layout-render name="${component.header.projectUser}"/>
            <tbody>
            <core:forEach var="item" items="${actionBean.project.projectUser}">
                <tr>
                    <td>${item.user.wholeName}</td>
                    <td class="action">
                        <security:allowed event="setPermission" bean="projectUserActionBean">
                            <stripes:form beanclass="cz.bbmri.action.ProjectUserActionBean">

                                <stripes:hidden name="projectId" value="${item.project.id}"/>
                                <stripes:hidden name="userId" value="${item.user.id}"/>

                                <stripes:select name="permissionId" value="${item.permission.id}">
                                    <stripes:options-collection collection="${permissionActionBean.all}" value="id"
                                                                label="name"/>
                                </stripes:select>

                                <format:message var="questionSetPermission"
                                                key="cz.bbmri.action.ProjectUserActionBean.questionSetProjectUser"/>

                                <stripes:submit name="setPermission"
                                                onclick="return confirm('${questionSetPermission}')"
                                                class="btn btn-primary"/>
                            </stripes:form>

                        </security:allowed>
                        <security:notAllowed event="setPermission" bean="projectUserActionBean">
                            <format:message key="cz.bbmri.entity.Permission.${item.permission.name}"/>
                        </security:notAllowed>
                    </td>
                    <td class="action">
                        <security:allowed event="remove" bean="projectUserActionBean">
                            <stripes:form beanclass="cz.bbmri.action.ProjectUserActionBean">

                                <stripes:hidden name="projectId" value="${item.project.id}"/>
                                <stripes:hidden name="userId" value="${item.user.id}"/>

                                <format:message var="question" key="${projectUserActionBean.removeQuestion}"/>
                                <stripes:submit name="remove" onclick="return confirm('${question}')"
                                                class="btn btn-danger"/>
                            </stripes:form>
                        </security:allowed>
                    </td>
                </tr>
            </core:forEach>
            </tbody>
        </table>

    </stripes:layout-component>
</stripes:layout-render>