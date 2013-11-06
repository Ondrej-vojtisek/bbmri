<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>
    <fieldset>
        <legend>
           <f:message key="biobank.roles"/></legend>
        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th><s:label name="bbmri.entities.webEntities.RoleDTO.user"/></th>
                <th><s:label name="bbmri.entities.webEntities.RoleDTO.permission"/></th>
            </tr>
            </thead>
            <tbody>

            <c:if test="${not editable}">
                <c:if test="${empty bean.administrators}">
                    <tr>
                        <td colspan="2"><f:message key="empty"/></td>
                    </tr>
                </c:if>
                <c:forEach items="${bean.administrators}" var="administrator">
                    <tr>
                        <td><c:out value="${administrator.user.wholeName}"/></td>
                        <td><c:out value="${administrator.permission}"/></td>
                    </tr>
                </c:forEach>
            </c:if>

            <c:if test="${editable}">

                <c:if test="${empty bean.administrators}">
                    <tr>
                        <td colspan="2"><f:message key="empty"/></td>
                    </tr>
                </c:if>
                <c:forEach items="${bean.administrators}" var="administrator">
                    <tr>
                        <td><c:out value="${administrator.user.wholeName}"/></td>
                        <td><s:form beanclass="${bean.name}">
                            <s:select name="permission" value="${administrator.permission}">
                                <s:options-enumeration enum="bbmri.entities.enumeration.Permission"/>
                            </s:select>

                            <s:submit name="setPermission" onclick="return confirm('Change permissions?')">
                                <s:param name="administratorId" value="${administrator.id}"/>
                                <s:param name="id" value="${bean.id}"/>
                                Nastav
                            </s:submit>
                        </s:form>
                        </td>
                        <td>
                            <s:form beanclass="${bean.name}">
                                <s:submit name="removeAdministrator" onclick="return confirm('Delete?')">
                                    <s:param name="administratorId" value="${administrator.id}"/>
                                    <s:param name="id" value="${bean.id}"/>
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