<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.project.ProjectActionBean.create" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.project.CreateProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.project.CreateProjectActionBean">
            <fieldset>
                <s:link beanclass="bbmri.action.project.CreateProjectActionBean" event="initial" class="btn btn-inverse">
                    <f:message key="back"/>
                </s:link>
                <legend><f:message key="bbmri.action.project.CreateProjectActionBean.secondStep"/></legend>
                <table>
                    <tr>
                        <th><s:label for="z1" name="project.name"/></th>
                        <td><s:text id="z1" name="project.name"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z4" name="project.principalInvestigator"/></th>
                        <td><s:text id="z4" name="project.mainInvestigator"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z5" name="project.homeInstitution"/></th>
                        <td><s:text id="z5" name="project.homeInstitution"/></td>
                    </tr>
                </table>
                <s:submit name="confirmGeneral" class="btn btn-primary"/>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>