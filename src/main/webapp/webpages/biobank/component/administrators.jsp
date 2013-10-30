<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>
    <s:useActionBean var="biobankBean" beanclass="bbmri.action.biobank.BiobankActionBean"/>


    <fieldset>
        <legend><f:message key="biobank.roles"/></legend>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><s:label name="bbmri.entities.webEntities.RoleDTO.user"/></th>
                <th><s:label name="bbmri.entities.webEntities.RoleDTO.permission"/></th>
            </tr>
            </thead>
            <tbody>

            <c:if test="${not editable}">
                <c:if test="${empty biobankBean.biobankRoles}">
                    <tr>
                        <td colspan="2"><f:message key="empty"/></td>
                    </tr>
                </c:if>
                <c:forEach items="${biobankBean.biobankRoles}" var="biobankAdministrator">
                    <tr>
                        <td><c:out value="${biobankAdministrator.user.wholeName}"/></td>
                        <td><c:out value="${biobankAdministrator.permission}"/></td>
                    </tr>
                </c:forEach>
            </c:if>

            <c:if test="${editable}">

                <c:if test="${empty biobankBean.biobankRoles}">
                    <tr>
                        <td colspan="2"><f:message key="empty"/></td>
                    </tr>
                </c:if>
                <c:forEach items="${biobankBean.biobankRoles}" var="biobankAdministrator">
                    <tr>
                        <td><c:out value="${biobankAdministrator.user.wholeName}"/></td>
                        <td><c:out value="${biobankAdministrator.permission}"/></td>
                        <td>
                            <s:form beanclass="bbmri.action.biobank.BiobankActionBean">
                                <s:select name="permission">
                                    <s:options-enumeration enum="bbmri.entities.enumeration.Permission"/>
                                </s:select>

                                <s:submit name="setPermission" onclick="return confirm('Change permissions?')">
                                    <s:param name="biobankAdministratorId" value="${biobankAdministrator.id}"/>
                                    <s:param name="id" value="${biobankBean.id}"/>
                                    Nastav
                                </s:submit>
                            </s:form>

                            <s:form beanclass="bbmri.action.biobank.BiobankActionBean">

                                <s:submit name="removeAdministrator" onclick="return confirm('Delete?')">
                                    <s:param name="biobankAdministratorId" value="${biobankAdministrator.id}"/>
                                    <s:param name="id" value="${biobankBean.id}"/>
                                    Odstran
                                </s:submit>
                            </s:form>
                        </td>
                    </tr>
                </c:forEach>

            </c:if>

            </tbody>
        </table>
    </fieldset>


</s:layout-definition>