<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="cz.bbmri.action.project.ProjectActionBean.create" var="title"/>
<s:useActionBean var="ab" beanclass="cz.bbmri.action.project.CreateProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">

        <s:form beanclass="cz.bbmri.action.project.CreateProjectActionBean">
            <fieldset>
                <legend><f:message key="cz.bbmri.action.project.CreateProjectActionBean.fourthStep"/></legend>

                <s:label for="cz.bbmri.entities.Project.annotation"/>
                <s:textarea name="project.annotation"/>

                <div class="form-actions">
                    <s:submit name="confirmStep4" class="btn btn-primary btnMargin"/>
                    <s:submit name="backFromStep4" class="btn btn-inverse"/>
                </div>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>