<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean beanclass="cz.bbmri.action.AttachmentTypeActionBean" var="attachmentTypeActionBean"/>

<s:layout-render name="${component.layout.content}"
                 primarymenu="project">

    <s:layout-component name="body">

        <s:layout-render name="${component.menu.project}" active="attachments"/>

        <s:form beanclass="cz.bbmri.action.AttachmentActionBean" class="form-inline">

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
                                            <s:file name="fileBean"/>
                                        </span>
                        <a href="#" class="btn fileupload-exists" data-dismiss="fileupload"><f:message
                                key="remove"/></a>
                    </div>

                    <s:select name="attachmentTypeId">
                        <s:option label="Not filled" value=""/>
                        <s:options-collection collection="${attachmentTypeActionBean.projectAttachmentType}" value="id"
                                              label="name"/>
                    </s:select>

                    <s:hidden name="projectId" value="${actionBean.projectId}"/>
                    <s:submit name="projectAttachmentUpload" class="btn btn-primary"/>
                </div>
            </div>

        </s:form>


    </s:layout-component>

</s:layout-render>