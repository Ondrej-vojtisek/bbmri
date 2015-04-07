<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="attachments">

    <stripes:layout-component name="body">

        <stripes:form beanclass="cz.bbmri.action.biobank.BiobankAttachmentsActionBean" class="form-inline">


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

                    <stripes:select name="biobankAttachmentType" class="btnMargin">
                        <stripes:options-enumeration enum="cz.bbmri.entity.enumeration.BiobankAttachmentType"/>
                    </stripes:select>

                    <stripes:hidden name="biobankId"  value="${actionBean.biobankId}"/>
                    <stripes:submit name="attachmentUpload" class="btn btn-primary"/>
                </div>
            </div>

        </stripes:form>

        <stripes:layout-render name="/webpages/biobank/detail/attachmentTable.jsp"/>

    </stripes:layout-component>
</stripes:layout-render>