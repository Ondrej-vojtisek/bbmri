<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="project.edit" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Project.EditProjectActionBean"/>

<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.Project.EditProjectActionBean">
            <s:hidden name="project.id"/>
            <s:hidden name="project.researchers"/>
            <fieldset>
                <legend><f:message key="project.edit"/></legend>
                <%@include file="/form/createProjectForm.jsp" %>

                <s:select id="b4" name="project.projectState">
                <s:options-enumeration enum="bbmri.entities.ProjectState"/>
                </s:select>
                <s:submit name="update"><f:message key="save"/></s:submit>
            </fieldset>


            <fieldset>
            <legend><f:message key="project.assigned_researchers"/></legend>
            <table border="1">
                <tr>
                    <th><f:message key="id"/></th>
                    <th><f:message key="name"/></th>
                    <th><f:message key="surname"/></th>
                </tr>

                <c:forEach items="${ab.researchers}" var="res">
                    <tr>
                        <td><c:out value="${res.id}"/></td>
                        <td><c:out value="${res.name}"/></td>
                        <td><c:out value="${res.surname}"/></td>
                        <td><s:checkbox name="selected" value="${res.id}"/></td>
                        <td> <s:link beanclass="bbmri.action.Project.EditProjectActionBean" event="changeOwnership">
                             <s:param name="researcher.id" value="${res.id}"/>pokus                            </s:link>
                        </td>
                    </tr>
                </c:forEach>
            </table>
               <s:submit name="removeAll"><f:message key="remove_selected"/></s:submit>

              </fieldset>
            <fieldset>
                 <legend><f:message key="all_researchers"/></legend>

            <table border="1">
                   <tr>
                    <th><f:message key="id"/></th>
                    <th><f:message key="name"/></th>
                    <th><f:message key="surname"/></th>
                </tr>

            <c:forEach items="${ab.freeResearchers}" var="res" varStatus="loop">

                    <tr>
                        <td><c:out value="${res.id}"/></td>
                        <td><c:out value="${res.name}"/></td>
                        <td><c:out value="${res.surname}"/></td>
                        <td><s:checkbox name="selectedApprove" value="${res.id}"/></td>
                    </tr>
                </c:forEach>
            </table>
                 <s:submit name="assignAll"><f:message key="assign_selected"/></s:submit>
            </fieldset>

        </s:form>

    </s:layout-component>
</s:layout-render>