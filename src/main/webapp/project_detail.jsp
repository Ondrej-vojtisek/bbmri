<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="project.edit" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Project.EditProjectActionBean"/>

<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.wholeName}"
                 primarymenu="project"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}">

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
                    <table cellspacing="0" class="tablesorter">
                        <thead>
                        <tr>
                            <th><s:label name="attachment.name"/></th>
                            <th><s:label name="attachment.type"/></th>
                            <th><s:label name="attachment.size"/></th>
                            <th><s:label name="attachment.importance"/></th>
                            <th class="noSort"><f:message key="actions"/></th>
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

            <s:link beanclass="bbmri.action.Project.ApproveProjectActionBean"><f:message key="back"/></s:link>
        </s:form>
    </s:layout-component>
</s:layout-render>