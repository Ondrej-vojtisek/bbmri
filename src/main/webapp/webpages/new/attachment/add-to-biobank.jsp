<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean beanclass="cz.bbmri.action.AttachmentTypeActionBean" var="attachmentTypeActionBean"/>

<stripes:layout-render name="${component.layout.content}"
                 primarymenu="biobank">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.biobank}" active="attachments"/>

        <stripes:form beanclass="cz.bbmri.action.AttachmentActionBean" class="form-inline">

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
                                            <stripes:file name="fileBean"/>
                                        </span>
                        <a href="#" class="btn fileupload-exists" data-dismiss="fileupload"><format:message
                                key="remove"/></a>
                    </div>

                    <stripes:select name="attachmentTypeId">
                        <stripes:option label="Not filled" value=""/>
                        <stripes:options-collection collection="${attachmentTypeActionBean.biobankAttachmentType}" value="id"
                                              label="name"/>
                    </stripes:select>

                    <stripes:hidden name="biobankId" value="${actionBean.biobankId}"/>
                    <stripes:submit name="biobankAttachmentUpload" class="btn btn-primary"/>
                </div>
            </div>

        </stripes:form>


    </stripes:layout-component>

</stripes:layout-render>