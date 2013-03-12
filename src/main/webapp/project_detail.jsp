<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="project.edit" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Project.EditProjectActionBean"/>

<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="primary_menu">
        <li class="active"><s:link href="/project_all.jsp"><f:message key="projects"/></s:link></li>
        <li><s:link href="/biobank_all.jsp"><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp"><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp"><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
        <li><s:link href="/project_all.jsp"><f:message key="all"/></s:link></li>
        <li><s:link href="/project_create.jsp"><f:message key="projects.createProject"/></s:link></li>
        <c:if test="${ab.loggedUser.ethicalCommitteeOfBiobank != null}">
            <li><s:link href="/project_approve.jsp"><f:message key="approve"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.Project.ApproveProjectActionBean">
            <table>
                <tr>
                    <th><s:label for="z1" name="project.name"/></th>
                    <td><s:text id="z1" readonly="true" name="project.name"/></td>
                </tr>
                <tr>
                    <th><s:label for="z2" name="project.fundingOrganization"/></th>
                    <td><s:text id="z2" readonly="true" name="project.fundingOrganization"/></td>
                </tr>
                <tr>
                    <th><s:label for="z3" name="project.projectState"/></th>
                    <td><s:text id="z3" readonly="true" name="project.projectState"/></td>
                </tr>

                <tr>
                    <th><s:label for="z4" name="project.judgedByUser"/></th>
                    <td><s:text id="z4" readonly="true" name="project.judgedByUser.wholeName"/></td>
                </tr>
                <tr>
                    <th><s:label for="z5" name="project.owner"/></th>
                    <td><s:text id="z5" readonly="true" name="project.owner.wholeName"/></td>
                </tr>
            </table>
            <s:textarea readonly="true" id="z5" name="project.annotation"></s:textarea>
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

            <s:link href="/project_approve.jsp"><f:message key="back"/></s:link>
        </s:form>
    </s:layout-component>
</s:layout-render>