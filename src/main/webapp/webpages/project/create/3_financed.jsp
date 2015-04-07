<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.action.project.ProjectActionBean.create" var="title"/>
<stripes:useActionBean var="ab" beanclass="cz.bbmri.action.project.CreateProjectActionBean"/>
<stripes:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.project.CreateProjectActionBean" class="form-horizontal">
            <fieldset>
                <legend><format:message key="cz.bbmri.action.project.CreateProjectActionBean.thirdStep"/></legend>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Project.fundingOrganization" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="project.fundingOrganization"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Project.approvedBy" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="project.approvedBy"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Project.approvalDate" class="control-label"/>
                    <div class="controls">
                        <div class="input-append date" id="dp" data-date="1-1-2014" data-date-format="dd-mm-yyyy">
                            <stripes:text name="project.approvalDate" readonly="true" value=""/>
                            <span class="add-on"><i class="icon-calendar"></i></span>
                        </div>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Project.approvalStorage" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="project.approvalStorage"/>
                    </div>
                </div>

                <div class="form-actions">
                    <stripes:submit name="confirmStep3" class="btn btn-primary btnMargin"/>
                    <stripes:submit name="backFromStep3" class="btn btn-inverse"/>
                </div>
            </fieldset>
        </stripes:form>
    </stripes:layout-component>

    <stripes:layout-component name="script">
        <script type="text/javascript">
            $(function () {
                $('#dp').datepicker();
            });
        </script>
    </stripes:layout-component>

</stripes:layout-render>