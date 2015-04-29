<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.action.ProjectCreateActionBean.create" var="title"/>

<stripes:layout-render name="${component.layout.content}" title="${title}"
                       primarymenu="project">

    <stripes:layout-component name="body">

        <fieldset>
            <legend><format:message key="cz.bbmri.action.ProjectCreateActionBean.secondStep"/></legend>

            <stripes:form beanclass="cz.bbmri.action.ProjectCreateActionBean" class="form-horizontal">

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Project.name" class="control-label">
                        <format:message key="cz.bbmri.entity.Project.name"/>
                    </stripes:label>

                    <div class="controls">
                        <stripes:text name="project.name"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Project.principalInvestigator" class="control-label">
                        <format:message key="cz.bbmri.entity.Project.principalInvestigator"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="project.principalInvestigator"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Project.homeInstitution" class="control-label">
                        <format:message key="cz.bbmri.entity.Project.homeInstitution"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="project.homeInstitution"/>
                    </div>
                </div>

                <div class="form-actions">
                    <stripes:submit name="third" class="btn btn-primary btnMargin"/>

                    <stripes:link beanclass="cz.bbmri.action.ProjectCreateActionBean" event="first"
                                  class="btn btn-inverse">
                        <format:message key="back"/>
                    </stripes:link>
                </div>
            </stripes:form>

        </fieldset>
    </stripes:layout-component>
</stripes:layout-render>