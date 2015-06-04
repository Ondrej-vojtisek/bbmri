<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="projectActionBean" beanclass="cz.bbmri.action.ProjectActionBean"/>
<core:set var="project" value="${actionBean.project}"/>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.project}" active="detail"/>

        <stripes:layout-render name="/webpages/project/component/hasMta.jsp"/>

        <table class="table table-bordered table-striped">
            <tr>
                <th width="30%"><format:message key="id"/></th>
                <td width="70%">${project.id}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Project.created"/></th>
                <td><format:formatDate value="${project.created}" type="both"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Project.name"/></th>
                <th>${project.name}</th>
            </tr>

            <core:if test="${project.clinicalTrial}">
                <tr>
                    <th><format:message key="cz.bbmri.entity.Project.eudraCtNumber"/></th>
                    <td>${project.eudraCtNumber}</td>
                </tr>
            </core:if>
            <tr>
                <th><format:message key="cz.bbmri.entity.Project.fundingOrganization"/></th>
                <td>${project.fundingOrganization}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Project.principalInvestigator"/></th>
                <td>${project.principalInvestigator}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Project.approvalStorage"/></th>
                <td>${project.approvalStorage}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Project.approvalDate"/></th>
                <td><format:formatDate value="${project.approvalDate}" type="both"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Project.projectState"/></th>
                <td><format:message key="cz.bbmri.entity.ProjectState.${project.projectState}"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Project.annotation"/></th>
                <td>${project.annotation}</td>
            </tr>


            </tbody>
        </table>


        <stripes:form beanclass="cz.bbmri.action.ProjectActionBean" class="form-horizontal">
            <div class="form-actions">
                <stripes:hidden name="id" value="${actionBean.project.id}"/>

                <core:if test="${actionBean.project.isNew}">
                    <security:allowed bean="projectActionBean" event="confirm">
                        <stripes:submit name="confirm" class="btn btn-primary btnMargin"/>
                    </security:allowed>

                    <security:allowed bean="projectActionBean" event="deny">
                        <stripes:submit name="deny" class="btn btn-danger btnMargin"/>
                    </security:allowed>
                </core:if>

                <%--DOČASNÉ--%>
                <%--<core:if test="${actionBean.project.isConfirmed}">--%>
                    <%--<security:allowed bean="projectActionBean" event="finish">--%>
                        <%--<stripes:submit name="finish" class="btn btn-primary btnMargin"/>--%>
                    <%--</security:allowed>--%>

                    <%--<security:allowed bean="projectActionBean" event="cancel">--%>
                        <%--<stripes:submit name="cancel" class="btn btn-danger btnMargin"/>--%>
                    <%--</security:allowed>--%>
                <%--</core:if>--%>

            </div>
        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>