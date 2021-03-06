<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="administrators">

    <s:layout-component name="body">

        <div class="form-actions">
            <s:link beanclass="cz.bbmri.action.project.FindProjectAdminActionBean" class="btn btn-primary">
                <s:param name="projectId" value="${actionBean.projectId}"/>
                <f:message key="cz.bbmri.action.project.ProjectActionBean.addAdministrator"/>
            </s:link>
        </div>

        <table class="table table-hover table-striped">

            <s:layout-render name="${actionBean.componentManager.sortableHeader}"
                             pagination="${actionBean.pagination}"/>

            <tbody>

            <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             collection="${actionBean.pagination.myPageList}"/>

            <c:forEach var="item" items="${actionBean.pagination.myPageList}">
                <tr>
                    <td>${item.user.wholeName}</td>

                    <td class="action">
                        <security:allowed event="setPermission">
                            <s:form beanclass="${actionBean.name}">

                                <s:hidden name="projectId" value="${actionBean.projectId}"/>
                                <s:hidden name="adminId" value="${item.id}"/>

                                <s:select name="permission" value="${administrator.permission}">
                                    <s:options-enumeration enum="cz.bbmri.entities.enumeration.Permission"/>
                                </s:select>

                                <f:message var="questionSet" key="${actionBean.name}.questionSetPermission"/>

                                <s:submit name="setPermission" onclick="return confirm('${questionSet}')"
                                          class="btn btn-primary"/>
                            </s:form>

                        </security:allowed>
                        <security:notAllowed event="setPermission">
                            ${item.permission}
                        </security:notAllowed>
                    </td>

                        <%--To distinguish if this is my record --%>
                    <c:set target="${actionBean}" property="userId" value="${item.user.id}"/>

                    <td class="action">
                        <security:allowed event="removeAdministrator">

                            <s:form beanclass="${actionBean.name}">
                                <s:hidden name="projectId" value="${actionBean.projectId}"/>
                                <s:hidden name="adminId" value="${item.id}"/>

                                <f:message var="question" key="${actionBean.removeQuestion}"/>
                                <s:submit name="removeAdministrator" onclick="return confirm('${question}')"
                                          class="btn btn-danger"/>
                            </s:form>

                        </security:allowed>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <s:layout-render name="/webpages/component/detail/sortableTable/pagination.jsp"
                         pagination="${actionBean.pagination}"/>


    </s:layout-component>
</s:layout-render>