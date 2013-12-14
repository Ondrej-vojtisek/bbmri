<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.project.ProjectActionBean.create" var="title"/>
<s:useActionBean var="ab" beanclass="cz.bbmri.action.project.CreateProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">


        <fieldset>
            <legend><f:message key="cz.bbmri.action.project.CreateProjectActionBean.secondStep"/></legend>

            <s:form beanclass="cz.bbmri.action.project.CreateProjectActionBean" class="form-horizontal">

                <div class="control-group">
                    <s:label for="name" class="control-label"/>
                    <div class="controls">
                        <s:text id="name" name="project.name"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="principalInvestigator" class="control-label"/>
                    <div class="controls">
                        <s:text id="principalInvestigator" name="project.principalInvestigator"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="homeInstitution" class="control-label"/>
                    <div class="controls">
                        <s:text id="homeInstitution" name="project.homeInstitution"/>
                    </div>
                </div>

                <div class="form-actions">
                    <s:submit name="confirmStep2" class="btn btn-primary btnMargin"/>

                    <s:link beanclass="cz.bbmri.action.project.CreateProjectActionBean" event="initial"
                            class="btn btn-inverse">
                        <f:message key="back"/>
                    </s:link>
                </div>
            </s:form>

        </fieldset>
    </s:layout-component>
</s:layout-render>