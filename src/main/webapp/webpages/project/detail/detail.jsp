<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.project.ProjectActionBean.detail" var="title"/>
<s:useActionBean var="projectBean" beanclass="bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="detail">

    <s:layout-component name="body">

        <s:form beanclass="${projectBean.name}">

            <c:set var="readonly" value="${true}"/>

            <security:allowed bean="projectBean" event="edit">
                <c:set var="readonly" value="${false}"/>
            </security:allowed>

            <table>
                <tr>
                    <th><s:label for="z1" name="project.name"/></th>
                    <td><s:text id="z1" name="project.name" readonly="${readonly}"/></td>
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
                    <td><s:text id="z4" name="project.mainInvestigator" readonly="${readonly}"/></td>
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

            <s:textarea id="z7" name="project.annotation" readonly="${readonly}"></s:textarea>
            <security:allowed bean="projectBean" event="edit">
                <s:submit name="update" class="btn btn-primary">
                    <s:param name="id" value="${projectBean.id}"/>
                </s:submit>
            </security:allowed>
        </s:form>

    </s:layout-component>
</s:layout-render>
