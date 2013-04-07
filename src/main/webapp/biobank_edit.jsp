<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="biobank.edit" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Biobank.BiobankActionBean"/>

<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="primary_menu">
        <li><s:link href="/project_my_projects.jsp"><f:message key="projects"/></s:link></li>
        <li class="active"><s:link href="/biobank_all.jsp"><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp"><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp"><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
        <li><s:link href="/biobank_all.jsp"><f:message key="biobanks_all"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/biobank_create.jsp"><f:message key="biobank_create"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.biobank != null}">
            <li><s:link href="/sample_approve_request.jsp"><f:message key="approve_sample_request"/></s:link></li>
            <li><s:link href="/sample_create.jsp"><f:message key="sample_create"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.Biobank.BiobankActionBean">
            <s:hidden name="biobank.id"/>
            <fieldset>
                <legend><f:message key="project.edit"/></legend>
                <%@include file="/form/createBiobankForm.jsp" %>
                <s:submit name="update"><f:message key="save"/></s:submit>
            </fieldset>

        </s:form>
        <s:form beanclass="bbmri.action.Biobank.BiobankActionBean">
        <fieldset>
            <legend><f:message key="biobank.administrators"/></legend>
                     <table id="sortableTable">
                         <thead>
                         <tr>
                             <th><f:message key="name"/></th>
                             <th><f:message key="surname"/></th>
                         </tr>
                         </thead>
                         <tbody>
                         <c:forEach items="${ab.administrators}" var="user">
                             <tr>
                                 <td><c:out value="${user.name}"/></td>
                                 <td><c:out value="${user.surname}"/></td>
                                 <td>
                                     <c:if test="${!user.equals(loggedUser)}">
                                     <s:checkbox name="selected" value="${user.id}"/></td>
                                 </c:if>
                                 <td><c:if test="${!user.equals(loggedUser)}">
                                     <s:link beanclass="bbmri.action.Project.EditProjectActionBean"
                                             event="changeOwnership">
                                         <s:param name="user.id" value="${user.id}"/><f:message
                                             key="give_ownership"/></s:link>
                                 </c:if></td>
                             </tr>
                         </c:forEach>
                         </tbody>
                     </table>
                     <s:submit name="removeAll"><f:message key="remove_selected"/></s:submit>
                 </fieldset>
            <fieldset>
                          <legend><f:message key="all_users"/></legend>
                          <p><i>(There will be a search for users, not list of all users)</i><p>
                          <table>
                              <tr>
                                  <th><f:message key="name"/></th>
                                  <th><f:message key="surname"/></th>
                              </tr>
                              <c:forEach items="${ab.freeUsers}" var="user" varStatus="loop">
                                  <tr>
                                      <td><c:out value="${user.name}"/></td>
                                      <td><c:out value="${user.surname}"/></td>
                                      <td><s:checkbox name="selectedApprove" value="${user.id}"/></td>
                                  </tr>
                              </c:forEach>
                          </table>
                          <s:submit name="assignAll"><f:message key="assign_selected"/></s:submit>
                      </fieldset>

        </s:form>
    </s:layout-component>
</s:layout-render>
