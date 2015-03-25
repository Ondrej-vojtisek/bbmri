<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%--<f:message key="cz.bbmri.action.project.ProjectActionBean.detail" var="title"/>--%>

<s:useActionBean var="adminFindBean" beanclass="cz.bbmri.action.project.FindProjectAdminActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="administrators">

    <s:layout-component name="body">

        <fieldset>
            <s:form beanclass="${actionBean.name}" class="form-horizontal">
                <s:hidden name="projectId"/>

                <s:layout-render name="/webpages/component/form/userFindInput.jsp"/>

                <div class="form-actions">
                    <s:submit name="find" class="btn btn-primary btnMargin"/>
                </div>
            </s:form>
        </fieldset>

        </br>

        <fieldset>
            <c:if test="${empty actionBean.results}">
                <p><f:message key="cz.bbmri.action.FindActionBean.noResults"/></p>
            </c:if>

            <c:if test="${not empty actionBean.results}">
                <legend><f:message key="cz.bbmri.action.FindActionBean.results"/></legend>
                <table class="table table-hover table-striped">
                    <s:layout-render name="/webpages/component/detail/user/header.jsp"/>
                    <tbody>
                    <c:forEach var="user" items="${actionBean.results}">
                        <tr>
                            <s:layout-render name="/webpages/component/detail/user/row.jsp" record="${user}"/>
                            <td class="action">
                                <span class="pull-right">
                                <s:form beanclass="cz.bbmri.action.project.ProjectAdministratorsActionBean">

                                    <s:select name="permission">
                                        <s:options-enumeration enum="cz.bbmri.entity.enumeration.Permission"/>
                                    </s:select>

                                    <s:hidden name="projectId"/>
                                    <s:hidden name="adminId" value="${user.id}"/>

                                    <s:submit name="addAdministrator" class="btn btn-primary"/>
                                </s:form>
                                    </span>>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </fieldset>

    </s:layout-component>
</s:layout-render>