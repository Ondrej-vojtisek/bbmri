<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.project.ProjectActionBean.create" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.project.CreateProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">


        <s:form beanclass="bbmri.action.project.CreateProjectActionBean" method="GET">
            <s:submit name="annotation" class="btn btn-inverse"/>
        </s:form>

        <s:form beanclass="bbmri.action.project.CreateProjectActionBean">
            <fieldset>
                <legend><f:message key="bbmri.action.project.CreateProjectActionBean.fifthStep"/></legend>
                <table>
                    <tr>
                        <th><s:label for="z1" name="project.name"/></th>
                        <td><s:text id="z1" name="project.name" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z2" name="project.fundingOrganization"/></th>
                        <td><s:text id="z2" name="project.fundingOrganization" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z3" name="project.approvedBy"/></th>
                        <td><s:text id="z3" name="project.approvedBy" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z6" name="project.approvalStorage"/></th>
                        <td><s:text id="z6" name="project.approvalStorage" readonly="true"/></td>
                    </tr>

                    <tr>
                        <th><s:label for="z4" name="project.principalInvestigator"/></th>
                        <td><s:text id="z4" name="project.mainInvestigator" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z5" name="project.homeInstitution"/></th>
                        <td><s:text id="z5" name="project.homeInstitution" readonly="true"/></td>
                    </tr>

                    <tr>
                        <th>
                            <p><s:label for="z7" name="project.annotation"/></p>
                        </th>
                    </tr>

                </table>

                <s:textarea id="z7" name="project.annotation" readonly="true"></s:textarea>

                <s:submit name="confirm" class="btn btn-primary"/>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>