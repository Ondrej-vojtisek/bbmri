<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="biobank.edit" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Biobank.BiobankActionBean"/>

<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}"
                 primarymenu="biobank"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}">

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
                              <c:forEach items="${ab.nonAdministrators}" var="user" varStatus="loop">
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
