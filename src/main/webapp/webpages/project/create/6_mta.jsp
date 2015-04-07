<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.action.project.ProjectActionBean.create" var="title"/>
<stripes:useActionBean var="ab" beanclass="cz.bbmri.action.project.CreateProjectActionBean"/>
<stripes:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.project.CreateProjectActionBean" class="form-inline">
            <fieldset>
                <legend><format:message key="cz.bbmri.action.project.CreateProjectActionBean.sixthStep"/></legend>

                <div class="form-actions">
                    <div class="fileupload fileupload-new" data-provides="fileupload">
                        <div class="input-append">
                            <div class="uneditable-input span3">
                                <i class="icon-file fileupload-exists"></i>
                                <span class="fileupload-preview"></span>
                            </div>
                        <span class="btn btn-file">
                            <span class="fileupload-new">Select file</span>
                            <span class="fileupload-exists">Change</span>
                            <stripes:file name="attachmentFileBean"/>
                        </span>
                            <a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Remove</a>
                        </div>


                    </div>
                    <stripes:submit name="backFromStep6" class="btn btn-inverse btnMargin"/>
                    <stripes:submit name="confirmStep6" class="btn btn-primary"/>
                </div>

            </fieldset>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>