<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.project.ProjectActionBean.create" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.project.CreateProjectActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">


        <s:form beanclass="bbmri.action.project.CreateProjectActionBean" method="GET">
            <s:submit name="annotation" class="btn btn-inverse"/>
        </s:form>

        <s:form beanclass="bbmri.action.project.CreateProjectActionBean" class="form-inline">
            <fieldset>
                <legend><f:message key="bbmri.action.project.CreateProjectActionBean.sixthStep"/></legend>

                <div class="fileupload fileupload-new" data-provides="fileupload">
                    <div class="input-append">
                        <div class="uneditable-input span3">
                            <i class="icon-file fileupload-exists"></i>
                            <span class="fileupload-preview"></span>
                        </div>
                        <span class="btn btn-file">
                            <span class="fileupload-new">Select file</span>
                            <span class="fileupload-exists">Change</span>
                            <s:file name="attachmentFileBean"/>
                        </span>
                        <a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Remove</a>

                    </div>
                        <s:submit name="mtaUpload" class="btn btn-primary"/>
                </div>

            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>