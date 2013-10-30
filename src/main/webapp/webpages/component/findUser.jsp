<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>
    <fieldset>
        <s:form beanclass="${bean.name}">
            <s:label name="user.name"/>
            <s:text name="userFind.name"/>
            <s:label name="user.surname"/>
            <s:text name="userFind.surname"/>
            <s:label name="user.email"/>
            <s:text name="userFind.email"/>
            <s:submit name="find"><f:message key="find"/></s:submit>
        </s:form>
    </fieldset>

    <fieldset>
        <s:form beanclass="${bean.name}">
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

                <c:if test="${empty bean.results}">
                    <tr>
                        <td colspan="3"><f:message key="empty"/></td>
                    </tr>
                </c:if>

                <c:forEach var="user" items="${bean.results}">
                    <tr>
                        <td>
                            <c:out value="${user.id}"/>

                        </td>

                        <td><c:out value="${user.name}"/></td>
                        <td><c:out value="${user.surname}"/></td>
                        <td>
                                <s:submit name="${eventName}">
                                <s:param name="id" value="${user.id}"/><f:message key="${eventName}"/>
                                </s:submit>
                                <%--<s:link beanclass="${bean.name}"--%>
                                <%--event="${eventName}">--%>
                                <%--<s:param name="id" value="${user.id}"/>--%>
                                <%--<f:message key="${eventName}"/>--%>
                                <%--</s:link>--%>
                        </td>
                            <%--<td>--%>
                            <%--<s:checkbox name="selected" value="${user.id}" />--%>
                            <%--</td>--%>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
            <%--<s:submit name="submitFind"><f:message key="pokus"/></s:submit>--%>
        </s:form>
    </fieldset>

</s:layout-definition>