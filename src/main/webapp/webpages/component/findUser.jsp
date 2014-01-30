<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="userBean" beanclass="cz.bbmri.action.user.UserActionBean"/>
<s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>
<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>

<s:layout-definition>
    <fieldset>
        <s:form beanclass="${findBean.name}" class="form-horizontal">
            <s:hidden name="id"/>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.User.name" class="control-label"/>
                <div class="controls">
                    <s:text name="userFind.name"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.User.surname" class="control-label"/>
                <div class="controls">
                    <s:text name="userFind.surname"/>
                </div>
            </div>

            <div class="control-group">
                <s:label for="cz.bbmri.entities.User.email" class="control-label"/>
                <div class="controls">
                    <s:text name="userFind.email"/>
                </div>
            </div>

            <div class="form-actions">
                <s:submit name="find" class="btn btn-primary btnMargin"/>

                <c:if test="${context == 'biobankCreate'}">
                    <s:form beanclass="${findBean.name}">
                        <s:submit name="backFromStep2" class="btn btn-inverse"/>
                    </s:form>
                </c:if>

            </div>
        </s:form>
    </fieldset>

    </br>

    <fieldset>
        <c:if test="${empty findBean.results}">
            <p><f:message key="cz.bbmri.action.FindActionBean.noResults"/></p>
        </c:if>


        <c:if test="${not empty findBean.results}">


            <legend><f:message key="cz.bbmri.action.FindActionBean.results"/></legend>
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th><f:message key="cz.bbmri.entities.User.id"/></th>
                    <th><f:message key="cz.bbmri.entities.User.wholeName"/></th>
                    <th><f:message key="cz.bbmri.entities.User.organization"/></th>
                </tr>
                </thead>
                <tbody>


                <c:forEach var="user" items="${findBean.results}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.wholeName}</td>
                        <td>${user.organization}</td>
                        <td class="action">
                            <c:choose>
                                <c:when test="${context == 'user'}">
                                    <s:link beanclass="${userBean.name}" event="detail" class="btn btn-primary">
                                        <s:param name="userId" value="${user.id}"/><f:message key="detail"/>
                                    </s:link>
                                </c:when>

                                <c:when test="${context == 'biobankCreate'}">
                                    <s:form beanclass="${findBean.name}">
                                        <s:submit name="confirmStep2" class="btn btn-primary btnMargin">
                                            <%-- Set id of new administrator --%>
                                            <s:param name="adminId" value="${user.id}"/>
                                        </s:submit>
                                    </s:form>
                                </c:when>

                                <c:when test="${context == 'biobank'}">
                                    <s:form beanclass="${biobankBean.name}">

                                        <s:select name="permission">
                                            <s:options-enumeration enum="cz.bbmri.entities.enumeration.Permission"/>
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
                                            <s:options-enumeration enum="cz.bbmri.entities.enumeration.Permission"/>
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