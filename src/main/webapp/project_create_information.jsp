<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="projects.createProject" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Project.ProjectActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}"
                 primarymenu="project"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}"
                 secondarymenu="project_create_project">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.Project.ProjectActionBean">
            <fieldset>
                <legend><f:message key="project_upload_new"/></legend>
                <li>Osoba, která v systému zakládá projekt, je odpovědná za uvedených správných a pravdivých údajů.</li>
                <li>Zadat lze pouze projekty, které byly schváleny etickou komisí instituce podávajícího</li>
                <li>Součástí založení projektu je nahrání naskenovaného formuláře Material Transfer Agreement (MTA), který je ke stažení zde:
                    <s:link href="/data/mta_form.pdf" target="blank"><f:message key="download_mta_form"/></s:link></li>
                <br>

                <br>
            </fieldset>
            <br>
            <s:link beanclass="bbmri.action.Project.ProjectActionBean" event="createProject" style="float: right;"><f:message key="continue"/></s:link>
        </s:form>
    </s:layout-component>
</s:layout-render>