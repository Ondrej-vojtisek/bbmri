<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%--<format:message key="cz.bbmri.action.project.ProjectActionBean.detail" var="title"/>--%>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="attachments">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.project.ProjectAttachmentsActionBean" class="form-inline">


            <div class="form-actions">
                <div class="fileupload fileupload-new" data-provides="fileupload">
                    <div class="input-append">
                        <div class="uneditable-input span3">
                            <i class="icon-file fileupload-exists"></i>
                            <span class="fileupload-preview"></span>
                        </div>
                                   <span class="btn btn-file">
                                       <span class="fileupload-new"><format:message key="selectFile"/></span>
                                       <span class="fileupload-exists"><format:message key="change"/></span>
                                       <stripes:file name="attachmentFileBean"/>
                                   </span>
                        <a href="#" class="btn fileupload-exists" data-dismiss="fileupload"><format:message
                                key="remove"/></a>

                    </div>

                    <stripes:select name="projectAttachmentType" class="btnMargin">
                        <stripes:options-enumeration enum="cz.bbmri.entity.enumeration.ProjectAttachmentType"/>
                    </stripes:select>

                    <stripes:submit name="attachmentUpload" class="btn btn-primary">
                        <stripes:param name="projectId" value="${actionBean.projectId}"/>
                    </stripes:submit>
                </div>
            </div>

        </stripes:form>

        <stripes:layout-render name="/webpages/project/detail/attachmentTable.jsp"/>

    </stripes:layout-component>
</stripes:layout-render>