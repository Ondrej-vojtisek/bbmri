<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>
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
                        <td class="action">
                            <s:form beanclass="${bean.name}">
                                    <s:select name="permission" value="${administrator.permission}">
                                        <s:options-enumeration enum="bbmri.entities.enumeration.Permission"/>
                                    </s:select>

                                <f:message var="questionSet" key="${bean.name}.questionSetPermission"/>

                                <s:submit name="setPermission" onclick="return confirm('${questionSet}')"
                                          class="btn btn-primary">
                                    <s:param name="administratorId" value="${administrator.id}"/>
                                    <s:param name="id" value="${bean.id}"/>
                                </s:submit>
                            </s:form>
                        </td>
                        <td class="action">
                            <s:form beanclass="${bean.name}">

                                <f:message var="question" key="${bean.name}.questionRemoveAdministrator"/>

                                <s:submit name="removeAdministrator" onclick="return confirm('${question}')"
                                          class="btn btn-danger">
                                    <s:param name="administratorId" value="${administrator.id}"/>
                                    <s:param name="id" value="${bean.id}"/>
                                </s:submit>
                            </s:form>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>


</s:layout-definition>