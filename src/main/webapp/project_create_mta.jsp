<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="projects.createProject" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Project.ProjectActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}"
                 primarymenu="project"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">
        <fieldset>
            <legend><f:message key="project_upload_mta"/> - <f:message key="second_step"/></legend>
            <s:form beanclass="bbmri.action.Project.ProjectActionBean">
                <s:file name="attachmentFileBean"></s:file>
                <s:submit name="uploadMTA" value="Upload"/>
            </s:form>
        </fieldset>
    </s:layout-component>
</s:layout-render>