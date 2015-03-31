<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.ProjectCreateActionBean.create" var="title"/>

<s:layout-render name="${component.layout.content}" title="${title}"
                 primarymenu="project">

    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="cz.bbmri.action.ProjectCreateActionBean.secondStep"/></legend>

            <s:form beanclass="cz.bbmri.action.ProjectCreateActionBean" class="form-horizontal">

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Project.name" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.name"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Project.principalInvestigator" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.principalInvestigator"/>
                    </div>
                </div>

                <div class="control-group">
                    <s:label for="cz.bbmri.entity.Project.homeInstitution" class="control-label"/>
                    <div class="controls">
                        <s:text name="project.homeInstitution"/>
                    </div>
                </div>

                <div class="form-actions">
                    <s:submit name="third" class="btn btn-primary btnMargin"/>

                    <s:link beanclass="cz.bbmri.action.ProjectCreateActionBean" event="first"
                            class="btn btn-inverse">
                        <f:message key="back"/>
                    </s:link>
                </div>
            </s:form>

        </fieldset>
    </s:layout-component>
</s:layout-render>