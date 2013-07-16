<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="project.edit" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Project.EditProjectActionBean"/>

<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}"
                 primarymenu="project"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.Project.EditProjectActionBean">
            <s:hidden name="project.id"/>
            <s:hidden name="project.users"/>
            <fieldset>
                <legend><f:message key="project.edit"/></legend>
                <%@include file="/form/createProjectForm.jsp" %>
                <s:select id="b4" name="project.projectState">
                    <s:options-enumeration enum="bbmri.entities.ProjectState"/>
                </s:select>
                <s:submit name="update"><f:message key="save"/></s:submit>
            </fieldset>

            <fieldset>
                <legend><f:message key="project.assigned_users"/></legend>
                <table id="sortableTable">
                    <thead>
                    <tr>
                        <th><f:message key="name"/></th>
                        <th><f:message key="surname"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${ab.users}" var="user">
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
                <s:submit name="removeAll" onclick="return confirm('Delete?')"><f:message key="remove_selected"/></s:submit>
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
        <s:form beanclass="bbmri.action.Project.EditProjectActionBean">

            <fieldset>
                <legend><f:message key="project_files"/></legend>
                <table>
                    <thead>
                    <tr>
                        <th><f:message key="attachment.name"/></th>
                        <th><f:message key="attachment.type"/></th>
                        <th><f:message key="attachment.size"/></th>
                        <th><f:message key="attachment.importance"/></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${ab.attachments}" var="attachment" varStatus="loop">
                        <tr>
                            <td><c:out value="${attachment.fileName}"/></td>
                            <td><c:out value="${attachment.contentType}"/></td>
                            <td><c:out value="${attachment.size}"/></td>
                            <td><f:message key="AttachmentType.${attachment.attachmentType}"/></td>
                            <td><s:link beanclass="bbmri.action.Project.EditProjectActionBean"
                                event="download">
                                <s:param name="attachment.id" value="${attachment.id}"/>
                                <f:message key="download"/></s:link>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>