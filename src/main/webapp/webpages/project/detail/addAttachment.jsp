<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.project.ProjectActionBean.detail" var="title"/>

<s:useActionBean var="projectBean" beanclass="bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="addAttachment">

    <s:layout-component name="body">

        <%--<s:form beanclass="bbmri.action.project.ProjectActionBean">--%>
        <%--<fieldset>--%>
        <%--<legend></legend>--%>
        <%--<s:select name="attachmentType">--%>
        <%--<s:options-enumeration enum="bbmri.entities.enumeration.AttachmentType"/>--%>
        <%--</s:select>--%>
        <%--<s:file name="attachmentFileBean"></s:file>--%>
        <%--<s:submit name="mtaUpload">--%>
        <%--<s:param name="id" value="${projectBean.id}"/>--%>
        <%--<f:message key="upload"/></s:submit>--%>
        <%--</fieldset>--%>

        <s:form beanclass="bbmri.action.project.ProjectActionBean" class="form-inline">

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
                        <s:options-enumeration enum="bbmri.entities.enumeration.AttachmentType"/>
                    </s:select>

                    <s:submit name="mtaUpload" class="btn btn-primary">
                        <s:param name="id" value="${projectBean.id}"/>
                    </s:submit>
                </div>

        </s:form>

    </s:layout-component>
</s:layout-render>