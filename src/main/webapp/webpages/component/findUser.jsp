<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="userBean" beanclass="bbmri.action.user.UserActionBean"/>
<s:useActionBean var="biobankBean" beanclass="bbmri.action.biobank.BiobankActionBean"/>
<s:useActionBean var="projectBean" beanclass="bbmri.action.project.ProjectActionBean"/>

<s:layout-definition>
    <fieldset>
        <s:form beanclass="${findBean.name}" method="GET">
            <s:hidden name="id"/>

            <s:label name="user.name"/>
            <s:text name="userFind.name"/>
            <s:label name="user.surname"/>
            <s:text name="userFind.surname"/>
            <s:label name="user.email"/>
            <s:text name="userFind.email"/>
            <s:submit name="find">
                <%--<s:param name="id" value="${findBean.id}"/>--%>
                <f:message key="find"/>
            </s:submit>
        </s:form>
    </fieldset>

    <fieldset>

        <legend><f:message key="results"/></legend>
        <table class="table table-hover table-striped">
            <thead>
            <tr>
                    <%--<th><s:label name="id"/></th>--%>
                    <%--<th><s:label name="name"/></th>--%>
                    <%--<th><s:label name="surname"/></th>--%>

                <th>id</th>
                <th>name</th>
                <th>surname</th>
            </tr>
            </thead>
            <tbody>

            <c:if test="${empty findBean.results}">
                <tr>
                    <td colspan="3"><f:message key="empty"/></td>
                </tr>
            </c:if>

            <c:forEach var="user" items="${findBean.results}">
                <tr>
                    <td>
                        <c:out value="${user.id}"/>

                    </td>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.surname}"/></td>
                    <td>


                        <c:choose>
                            <c:when test="${context == 'user'}">
                                <s:link beanclass="${userBean.name}" event="detail">
                                    <s:param name="id" value="${user.id}"/><f:message key="detail"/>
                                </s:link>
                            </c:when>
                            <c:when test="${context == 'biobank'}">
                                <s:form beanclass="${biobankBean.name}">
                                    <s:select name="permission">
                                        <s:options-enumeration enum="bbmri.entities.enumeration.Permission"/>
                                    </s:select>

                                    <s:submit name="addAdministrator">
                                        <s:param name="id" value="${findBean.id}"/>
                                        <s:param name="adminId" value="${user.id}"/>
                                        <f:message key="addAdministrator"/>
                                    </s:submit>
                                </s:form>
                            </c:when>

                            <c:when test="${context == 'project'}">
                                <s:form beanclass="${projectBean.name}">
                                    <s:select name="permission">
                                        <s:options-enumeration enum="bbmri.entities.enumeration.Permission"/>
                                    </s:select>

                                    <s:submit name="addAdministrator">
                                        <s:param name="id" value="${findBean.id}"/>
                                        <s:param name="adminId" value="${user.id}"/>
                                        <f:message key="addAdministrator"/>
                                    </s:submit>
                                </s:form>
                            </c:when>

                            <c:otherwise>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </fieldset>

</s:layout-definition>