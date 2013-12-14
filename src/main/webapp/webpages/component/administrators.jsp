<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>
    <table class="table table-hover table-striped">
        <thead>
        <tr>
            <th><s:label name="cz.bbmri.entities.webEntities.RoleDTO.user"/></th>
            <th><s:label name="cz.bbmri.entities.webEntities.RoleDTO.permission"/></th>
        </tr>
        </thead>
        <tbody>

        <c:if test="${empty bean.administrators}">
            <tr>
                <td colspan="2"><f:message key="empty"/></td>
            </tr>
        </c:if>

        <c:forEach items="${bean.administrators}" var="administrator">
            <tr>
                <td><c:out value="${administrator.user.wholeName}"/></td>

                <td class="action">
                    <security:allowed bean="bean" event="setPermission">


                        <s:form beanclass="${bean.name}">
                            <s:select name="permission" value="${administrator.permission}">
                                <s:options-enumeration enum="cz.bbmri.entities.enumeration.Permission"/>
                            </s:select>

                            <f:message var="questionSet" key="${bean.name}.questionSetPermission"/>

                            <s:submit name="setPermission" onclick="return confirm('${questionSet}')"
                                      class="btn btn-primary">
                                <s:param name="adminId" value="${administrator.id}"/>
                                <s:param name="id" value="${bean.id}"/>
                            </s:submit>
                        </s:form>

                    </security:allowed>
                    <security:notAllowed bean="bean" event="setPermission">
                        ${administrator.permission}
                    </security:notAllowed>
                </td>

                <%--To distinguish if this is my record --%>

                <c:set target="${bean}" property="userAdminId" value="${administrator.user.id}"/>


                <td class="action">
                    <security:allowed bean="bean" event="removeAdministrator">

                        <s:form beanclass="${bean.name}">

                            <f:message var="question" key="${bean.removeQuestion}"/>

                            <s:submit name="removeAdministrator" onclick="return confirm('${question}')"
                                      class="btn btn-danger">
                                <s:param name="adminId" value="${administrator.id}"/>
                                <s:param name="id" value="${bean.id}"/>
                            </s:submit>
                        </s:form>
                    </security:allowed>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</s:layout-definition>