<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="administrators">

    <stripes:layout-component name="body">

        <div class="form-actions">
            <stripes:link beanclass="cz.bbmri.action.project.FindProjectAdminActionBean" class="btn btn-primary">
                <stripes:param name="projectId" value="${actionBean.projectId}"/>
                <format:message key="cz.bbmri.action.project.ProjectActionBean.addAdministrator"/>
            </stripes:link>
        </div>

        <table class="table table-hover table-striped">

            <stripes:layout-render name="${actionBean.componentManager.sortableHeader}"
                             pagination="${actionBean.pagination}"/>

            <tbody>

            <stripes:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             collection="${actionBean.pagination.myPageList}"/>

            <core:forEach var="item" items="${actionBean.pagination.myPageList}">
                <tr>
                    <td>${item.user.wholeName}</td>

                    <td class="action">
                        <security:allowed event="setPermission">
                            <stripes:form beanclass="${actionBean.name}">

                                <stripes:hidden name="projectId" value="${actionBean.projectId}"/>
                                <stripes:hidden name="adminId" value="${item.id}"/>

                                <stripes:select name="permission" value="${administrator.permission}">
                                    <stripes:options-enumeration enum="cz.bbmri.entity.enumeration.Permission"/>
                                </stripes:select>

                                <format:message var="questionSet" key="${actionBean.name}.questionSetPermission"/>

                                <stripes:submit name="setPermission" onclick="return confirm('${questionSet}')"
                                          class="btn btn-primary"/>
                            </stripes:form>

                        </security:allowed>
                        <security:notAllowed event="setPermission">
                            ${item.permission}
                        </security:notAllowed>
                    </td>

                        <%--To distinguish if this is my record --%>
                    <core:set target="${actionBean}" property="userId" value="${item.user.id}"/>

                    <td class="action">
                        <security:allowed event="removeAdministrator">

                            <stripes:form beanclass="${actionBean.name}">
                                <stripes:hidden name="projectId" value="${actionBean.projectId}"/>
                                <stripes:hidden name="adminId" value="${item.id}"/>

                                <format:message var="question" key="${actionBean.removeQuestion}"/>
                                <stripes:submit name="removeAdministrator" onclick="return confirm('${question}')"
                                          class="btn btn-danger"/>
                            </stripes:form>

                        </security:allowed>
                    </td>
                </tr>
            </core:forEach>
            </tbody>
        </table>

        <stripes:layout-render name="/webpages/component/detail/sortableTable/pagination.jsp"
                         pagination="${actionBean.pagination}"/>


    </stripes:layout-component>
</stripes:layout-render>