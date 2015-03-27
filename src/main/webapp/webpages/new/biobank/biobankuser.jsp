<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean beanclass="cz.bbmri.action.PermissionActionBean" var="permissionActionBean"/>
<s:useActionBean beanclass="cz.bbmri.action.BiobankUserActionBean" var="biobankUserActionBean"/>

<s:layout-render name="${component.layout.content}"
                 primarymenu="biobank">

    <s:layout-component name="body">

        <s:layout-render name="${component.menu.biobank}" active="biobankuser"/>

        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th><f:message key="cz.bbmri.entity.BiobankUser.user"/></th>
                <th><f:message key="cz.bbmri.entity.Permission"/></th>
            </tr>
            </thead>

            <tbody>


            <c:forEach var="item" items="${actionBean.biobank.biobankUser}">
                <tr>
                    <td>${item.user.wholeName}</td>
                    <td class="action">
                        <security:allowed event="setPermission" bean="biobankUserActionBean">
                            <s:form beanclass="cz.bbmri.action.BiobankUserActionBean">

                                <s:hidden name="biobankId" value="${item.biobank.id}"/>
                                <s:hidden name="userId" value="${item.user.id}"/>

                                <s:select name="permissionId" value="${item.permission.id}">
                                    <s:options-collection collection="${permissionActionBean.all}" value="id"
                                                          label="name"/>
                                </s:select>

                                <f:message var="questionSetPermission" key="cz.bbmri.action.BiobankUserActionBean.questionSetBiobankUser"/>

                                <s:submit name="setPermission" onclick="return confirm('${questionSetPermission}')"
                                          class="btn btn-primary"/>
                            </s:form>

                        </security:allowed>
                        <security:notAllowed event="setPermission" bean="biobankUserActionBean">
                            <f:message key="cz.bbmri.entity.Permission.${item.permission.name}"/>
                        </security:notAllowed>
                    </td>
                    <td class="action">
                        <security:allowed event="remove" bean="biobankUserActionBean">
                            <s:form beanclass="cz.bbmri.action.BiobankUserActionBean">

                                <s:hidden name="biobankId" value="${item.biobank.id}"/>
                                <s:hidden name="userId" value="${item.user.id}"/>

                                <f:message var="question" key="${biobankUserActionBean.removeQuestion}"/>
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