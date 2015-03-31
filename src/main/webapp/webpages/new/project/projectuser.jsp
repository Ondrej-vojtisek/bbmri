<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean beanclass="cz.bbmri.action.PermissionActionBean" var="permissionActionBean"/>
<s:useActionBean beanclass="cz.bbmri.action.ProjectUserActionBean" var="projectUserActionBean"/>

<s:layout-render name="${component.layout.content}"
                 primarymenu="project">

    <s:layout-component name="body">

        <s:layout-render name="${component.menu.project}" active="projectuser"/>

        <table class="table table-hover table-striped">

            <s:layout-render name="${component.header.projectUser}"/>

            <tbody>


            <c:forEach var="item" items="${actionBean.project.projectUser}">
                <tr>
                    <td>${item.user.wholeName}</td>
                    <td class="action">
                        <security:allowed event="setPermission" bean="projectUserActionBean">
                            <s:form beanclass="cz.bbmri.action.ProjectUserActionBean">

                                <s:hidden name="projectId" value="${item.project.id}"/>
                                <s:hidden name="userId" value="${item.user.id}"/>

                                <s:select name="permissionId" value="${item.permission.id}">
                                    <s:options-collection collection="${permissionActionBean.all}" value="id"
                                                          label="name"/>
                                </s:select>

                                <f:message var="questionSetPermission" key="cz.bbmri.action.ProjectUserActionBean.questionSetProjectUser"/>

                                <s:submit name="setPermission" onclick="return confirm('${questionSetPermission}')"
                                          class="btn btn-primary"/>
                            </s:form>

                        </security:allowed>
                        <security:notAllowed event="setPermission" bean="projectUserActionBean">
                            <f:message key="cz.bbmri.entity.Permission.${item.permission.name}"/>
                        </security:notAllowed>
                    </td>
                    <td class="action">
                        <security:allowed event="remove" bean="projectUserActionBean">
                            <s:form beanclass="cz.bbmri.action.ProjectUserActionBean">

                                <s:hidden name="projectId" value="${item.project.id}"/>
                                <s:hidden name="userId" value="${item.user.id}"/>

                                <f:message var="question" key="${projectUserActionBean.removeQuestion}"/>
                                <s:submit name="remove" onclick="return confirm('${question}')"
                                          class="btn btn-danger"/>
                            </s:form>
                        </security:allowed>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </s:layout-component>
</s:layout-render>