<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%--<f:message key="cz.bbmri.action.project.ProjectActionBean.detail" var="title"/>--%>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="attachments">

    <s:layout-component name="body">

        <s:layout-render name="/webpages/project/detail/attachmentTable.jsp"/>

        <s:form beanclass="cz.bbmri.action.project.ProjectAttachmentsActionBean" class="form-inline">


            <div class="form-actions">
                <div class="fileupload fileupload-new" data-provides="fileupload">
                    <div class="input-append">
                        <div class="uneditable-input span3">
                            <i class="icon-file fileupload-exists"></i>
                            <span class="fileupload-preview"></span>
                        </div>
                                   <span class="btn btn-file">
                                       <span class="fileupload-new"><f:message key="selectFile"/></span>
                                       <span class="fileupload-exists"><f:message key="change"/></span>
                                       <s:file name="attachmentFileBean"/>
                                   </span>
                        <a href="#" class="btn fileupload-exists" data-dismiss="fileupload"><f:message
                                key="remove"/></a>

                    </div>

                    <s:select name="attachmentType" class="btnMargin">
                        <s:options-enumeration enum="cz.bbmri.entities.enumeration.AttachmentType"/>
                    </s:select>

                    <s:submit name="attachmentUpload" class="btn btn-primary">
                        <s:param name="projectId" value="${actionBean.projectId}"/>
                    </s:submit>
                </div>
            </div>

        </s:form>

    </s:layout-component>
</s:layout-render>