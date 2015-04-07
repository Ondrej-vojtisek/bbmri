<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean beanclass="cz.bbmri.action.PermissionActionBean" var="permissionActionBean"/>
<stripes:useActionBean beanclass="cz.bbmri.action.BiobankUserActionBean" var="biobankUserActionBean"/>

<stripes:layout-render name="${component.layout.content}"
                 primarymenu="biobank">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.biobank}" active="biobankuser"/>

        <%--Set authProjectId of AuthotizationActionBean to enable security tag--%>
        <core:set target="${biobankUserActionBean}" property="authBiobankId" value="${actionBean.id}"/>

        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th><format:message key="cz.bbmri.entity.BiobankUser.user"/></th>
                <th><format:message key="cz.bbmri.entity.Permission"/></th>
            </tr>
            </thead>

            <tbody>

            <core:forEach var="item" items="${actionBean.biobank.biobankUser}">
                <tr>
                    <td>${item.user.wholeName}</td>


                    <td class="action">
                        <security:allowed event="setPermission" bean="biobankUserActionBean">
                            <stripes:form beanclass="cz.bbmri.action.BiobankUserActionBean">

                                <stripes:hidden name="biobankId" value="${item.biobank.id}"/>
                                <stripes:hidden name="userId" value="${item.user.id}"/>

                                <stripes:select name="permissionId" value="${item.permission.id}">
                                    <stripes:options-collection collection="${permissionActionBean.all}" value="id"
                                                          label="name"/>
                                </stripes:select>

                                <format:message var="questionSetPermission"
                                           key="cz.bbmri.action.BiobankUserActionBean.questionSetBiobankUser"/>

                                <stripes:submit name="setPermission" onclick="return confirm('${questionSetPermission}')"
                                          class="btn btn-primary"/>
                            </stripes:form>

                        </security:allowed>
                        <security:notAllowed event="setPermission" bean="biobankUserActionBean">
                            <format:message key="cz.bbmri.entity.Permission.${item.permission.name}"/>
                        </security:notAllowed>
                    </td>
                    <td class="action">
                        <security:allowed event="remove" bean="biobankUserActionBean">
                            <stripes:form beanclass="cz.bbmri.action.BiobankUserActionBean">

                                <stripes:hidden name="biobankId" value="${item.biobank.id}"/>
                                <stripes:hidden name="userId" value="${item.user.id}"/>

                                <format:message var="question" key="${biobankUserActionBean.removeQuestion}"/>
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