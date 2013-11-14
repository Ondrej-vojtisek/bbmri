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
            <br>
            <s:submit name="find" class="btn btn-primary"/>
        </s:form>
    </fieldset>

    </br>

    <fieldset>
    <c:if test="${empty findBean.results}">
            <p><f:message key="bbmri.action.FindActionBean.noResults"/></p>
    </c:if>


    <c:if test="${not empty findBean.results}">


        <legend><f:message key="bbmri.action.FindActionBean.results"/></legend>
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


            <c:forEach var="user" items="${findBean.results}">
                <tr>
                    <td>
                        <c:out value="${user.id}"/>

                    </td>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.surname}"/></td>
                    <td class="action">
                        <c:choose>
                            <c:when test="${context == 'user'}">
                                <s:link beanclass="${userBean.name}" event="detail" class="btn btn-primary">
                                    <s:param name="id" value="${user.id}"/><f:message key="detail"/>
                                </s:link>
                            </c:when>

                            <c:when test="${context == 'biobankCreate'}">
                                <s:form beanclass="${findBean.name}">

                                    <s:submit name="addAdministrator" class="btn btn-primary">
                                        <%-- Set id of new administrator --%>
                                        <s:param name="adminId" value="${user.id}"/>
                                    </s:submit>
                                </s:form>
                            </c:when>

                            <c:when test="${context == 'biobank'}">
                                <s:form beanclass="${biobankBean.name}">

                                    <s:select name="permission">
                                        <s:options-enumeration enum="bbmri.entities.enumeration.Permission"/>
                                    </s:select>

                                    <s:submit name="addAdministrator" class="btn btn-primary">
                                        <s:param name="id" value="${findBean.id}"/>
                                        <s:param name="adminId" value="${user.id}"/>
                                    </s:submit>
                                </s:form>
                            </c:when>

                            <c:when test="${context == 'project'}">
                                <s:form beanclass="${projectBean.name}">

                                    <s:select name="permission">
                                        <s:options-enumeration enum="bbmri.entities.enumeration.Permission"/>
                                    </s:select>

                                    <s:submit name="addAdministrator" class="btn btn-primary">
                                        <s:param name="id" value="${findBean.id}"/>
                                        <s:param name="adminId" value="${user.id}"/>
                                    </s:submit>
                                </s:form>
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    </fieldset>

</s:layout-definition>