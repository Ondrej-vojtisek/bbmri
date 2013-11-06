<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="projects.createProject" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.project.CreateProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">
        <s:errors/>

        <s:form beanclass="bbmri.action.project.CreateProjectActionBean" method="GET">
            <s:submit name="general">
                <f:message key="back"/>
            </s:submit>
        </s:form>

        <s:form beanclass="bbmri.action.project.CreateProjectActionBean">
            <fieldset>


                <legend><f:message key="project_upload_new"/> - <f:message key="first_step"/></legend>
                <table>
                    <tr>
                        <th><s:label for="z2" name="project.fundingOrganization"/></th>
                        <td><s:text id="z2" name="project.fundingOrganization"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z3" name="project.approvedBy"/></th>
                        <td><s:text id="z3" name="project.approvedBy"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z4" name="project.created"/></th>
                        <td><s:text id="z4" name="project.created"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z6" name="project.approvalStorage"/></th>
                        <td><s:text id="z6" name="project.approvalStorage"/></td>
                    </tr>
                </table>

                <s:submit name="financedConfirm"><f:message key="confirm"/></s:submit>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>