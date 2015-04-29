<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.action.ProjectCreateActionBean.create" var="title"/>

<stripes:layout-render name="${component.layout.content}" title="${title}"
                       primarymenu="project">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.ProjectCreateActionBean" class="form-horizontal">
            <fieldset>
                <legend><format:message key="cz.bbmri.action.ProjectCreateActionBean.thirdStep"/></legend>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Project.fundingOrganization" class="control-label">
                        <format:message key="cz.bbmri.entity.Project.fundingOrganization"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="project.fundingOrganization"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Project.approvedBy" class="control-label">
                        <format:message key="cz.bbmri.entity.Project.approvedBy"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="project.approvedBy"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Project.approvalDate" class="control-label">
                        <format:message key="cz.bbmri.entity.Project.approvalDate"/>
                    </stripes:label>
                    <div class="controls">
                        <div class="input-append date" id="dp" data-date="${actionBean.today}"
                             data-date-format="dd-mm-yyyy">
                            <stripes:text name="project.approvalDate" readonly="true" value=""/>
                            <span class="add-on"><i class="icon-calendar"></i></span>
                        </div>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entity.Project.approvalStorage" class="control-label">
                        <format:message key="cz.bbmri.entity.Project.approvalStorage"/>
                    </stripes:label>
                    <div class="controls">
                        <stripes:text name="project.approvalStorage"/>
                    </div>
                </div>

                <div class="form-actions">
                    <stripes:submit name="fourth" class="btn btn-primary btnMargin"/>
                    <stripes:submit name="backToSecond" class="btn btn-inverse"/>
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